package com.vk.directop.grandstore.applist.data.repository

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import com.vk.directop.grandstore.data.remote.CatalogApi
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GamesRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
    private val mapper: GameMapper,
) : GamesRepository {

    override suspend fun getGames(): List<Game> =
        api.getCatalog().map(mapper::toDomain)

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = LinkedHashMap<String, Float>(ids.size)
        for (id in ids) {
            ratings[id] = api.getRating(id)
        }
        return ratings
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async { id to api.getRating(id) }
        }.awaitAll().toMap()
    }
}

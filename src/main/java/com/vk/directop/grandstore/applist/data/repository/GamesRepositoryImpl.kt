package com.vk.directop.grandstore.applist.data.repository

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import com.vk.directop.grandstore.data.remote.CatalogApi
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
    private val mapper: GameMapper,
) : GamesRepository {

    override suspend fun getGames(): List<Game> =
        api.getCatalog().map(mapper::toDomain)
}

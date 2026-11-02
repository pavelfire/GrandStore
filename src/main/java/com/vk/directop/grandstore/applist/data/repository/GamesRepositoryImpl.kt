package com.vk.directop.grandstore.applist.data.repository

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.applist.data.source.HardcodedGamesDataSource
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val dataSource: HardcodedGamesDataSource,
    private val mapper: GameMapper,
) : GamesRepository {

    override fun getGames(): List<Game> =
        dataSource.getGames().map(mapper::toDomain)
}

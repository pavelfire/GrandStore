package com.vk.directop.grandstore.applist.data.repository

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.applist.data.source.HardcodedGamesDataSource
import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.applist.domain.repository.GamesRepository

class GamesRepositoryImpl(
    private val dataSource: HardcodedGamesDataSource = HardcodedGamesDataSource(),
    private val mapper: GameMapper = GameMapper(),
) : GamesRepository {

    override fun getGames(): List<Game> =
        dataSource.getGames().map(mapper::toDomain)
}

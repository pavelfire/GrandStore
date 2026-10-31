package com.vk.directop.grandstore.detailcard.data.repository

import com.vk.directop.grandstore.detailcard.data.mapper.GameMapper
import com.vk.directop.grandstore.detailcard.data.source.HardcodedGamesDataSource
import com.vk.directop.grandstore.detailcard.domain.model.Game
import com.vk.directop.grandstore.detailcard.domain.repository.GamesRepository

class GamesRepositoryImpl(
    private val dataSource: HardcodedGamesDataSource = HardcodedGamesDataSource(),
    private val mapper: GameMapper = GameMapper(),
) : GamesRepository {

    override fun getGameById(id: String): Game? =
        games().find { it.id == id }

    override fun getShowcasePages(): List<List<Game>> =
        games()
            .take(SHOWCASE_PAGE_SIZE * SHOWCASE_PAGE_COUNT)
            .chunked(SHOWCASE_PAGE_SIZE)

    private fun games(): List<Game> =
        dataSource.getGames().map(mapper::toDomain)

    private companion object {
        const val SHOWCASE_PAGE_SIZE = 3
        const val SHOWCASE_PAGE_COUNT = 3
    }
}

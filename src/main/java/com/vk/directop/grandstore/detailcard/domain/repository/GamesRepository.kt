package com.vk.directop.grandstore.detailcard.domain.repository

import com.vk.directop.grandstore.detailcard.domain.model.Game

interface GamesRepository {
    fun getGameById(id: String): Game?
    fun getShowcasePages(): List<List<Game>>
}

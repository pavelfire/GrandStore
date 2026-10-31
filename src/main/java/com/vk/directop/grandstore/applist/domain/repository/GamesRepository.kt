package com.vk.directop.grandstore.applist.domain.repository

import com.vk.directop.grandstore.applist.domain.model.Game

interface GamesRepository {
    fun getGames(): List<Game>
}

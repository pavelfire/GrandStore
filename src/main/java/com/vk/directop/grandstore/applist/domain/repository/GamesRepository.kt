package com.vk.directop.grandstore.applist.domain.repository

import com.vk.directop.grandstore.applist.domain.model.Game

interface GamesRepository {
    suspend fun getGames(): List<Game>

    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>

    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}

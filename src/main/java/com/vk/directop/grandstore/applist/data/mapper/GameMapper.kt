package com.vk.directop.grandstore.applist.data.mapper

import com.vk.directop.grandstore.applist.data.dto.GameDto
import com.vk.directop.grandstore.applist.domain.model.Game
import javax.inject.Inject

class GameMapper @Inject constructor() {
    fun toDomain(dto: GameDto): Game = Game(
        id = dto.id,
        title = dto.title,
        description = dto.description,
        category = dto.category,
        image = dto.image,
    )
}

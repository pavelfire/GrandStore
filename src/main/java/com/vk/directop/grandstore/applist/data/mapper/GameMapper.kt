package com.vk.directop.grandstore.applist.data.mapper

import com.vk.directop.grandstore.applist.data.dto.GameDto
import com.vk.directop.grandstore.applist.domain.model.Game

class GameMapper {
    fun toDomain(dto: GameDto): Game = Game(
        id = dto.id,
        title = dto.title,
        description = dto.description,
        category = dto.category,
        image = dto.image,
    )
}

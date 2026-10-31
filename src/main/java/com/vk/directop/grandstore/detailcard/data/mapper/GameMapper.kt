package com.vk.directop.grandstore.detailcard.data.mapper

import com.vk.directop.grandstore.detailcard.data.dto.GameDto
import com.vk.directop.grandstore.detailcard.domain.model.Game

class GameMapper {
    fun toDomain(dto: GameDto): Game = Game(
        id = dto.id,
        title = dto.title,
        description = dto.description,
        category = dto.category,
        image = dto.image,
    )
}

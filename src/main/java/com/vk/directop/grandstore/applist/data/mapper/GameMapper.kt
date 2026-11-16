package com.vk.directop.grandstore.applist.data.mapper

import com.vk.directop.grandstore.applist.domain.model.Game
import com.vk.directop.grandstore.data.remote.dto.CatalogItemDto
import javax.inject.Inject

class GameMapper @Inject constructor() {
    fun toDomain(dto: CatalogItemDto): Game = Game(
        id = dto.id,
        title = dto.name,
        description = dto.description,
        category = dto.category,
        image = dto.iconUrl,
    )
}

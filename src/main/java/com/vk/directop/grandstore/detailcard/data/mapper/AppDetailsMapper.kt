package com.vk.directop.grandstore.detailcard.data.mapper

import com.vk.directop.grandstore.data.remote.dto.AppDetailsDto
import com.vk.directop.grandstore.detailcard.domain.model.AppDetails
import javax.inject.Inject

class AppDetailsMapper @Inject constructor() {
    fun toDomain(dto: AppDetailsDto): AppDetails = AppDetails(
        id = dto.id,
        name = dto.name,
        developer = dto.developer,
        category = dto.category,
        ageRating = dto.ageRating,
        size = dto.size,
        iconUrl = dto.iconUrl,
        screenshotUrls = dto.screenshotUrlList,
        description = dto.description,
    )
}

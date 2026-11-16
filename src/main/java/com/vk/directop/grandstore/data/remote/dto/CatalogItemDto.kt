package com.vk.directop.grandstore.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CatalogItemDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String,
)

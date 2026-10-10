package com.vk.directop.grandstore.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AppListDestination

@Serializable
data class DetailDestination(val gameId: String)

package com.vk.directop.grandstore.data.remote

import com.vk.directop.grandstore.data.remote.dto.AppDetailsDto
import com.vk.directop.grandstore.data.remote.dto.CatalogItemDto
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogItemDto>

    @GET("catalog/{id}")
    suspend fun getApp(@Path("id") id: String): AppDetailsDto

    @Suppress("UNUSED_PARAMETER")
    suspend fun getRating(id: String): Float {
        delay(1_500)
        return (30..50).random() / 10f
    }
}

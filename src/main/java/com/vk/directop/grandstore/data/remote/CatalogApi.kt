package com.vk.directop.grandstore.data.remote

import com.vk.directop.grandstore.data.remote.dto.AppDetailsDto
import com.vk.directop.grandstore.data.remote.dto.CatalogItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogItemDto>

    @GET("catalog/{id}")
    suspend fun getApp(@Path("id") id: String): AppDetailsDto
}

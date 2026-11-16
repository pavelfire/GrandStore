package com.vk.directop.grandstore.applist.data.repository

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.data.remote.CatalogApi
import com.vk.directop.grandstore.data.remote.dto.AppDetailsDto
import com.vk.directop.grandstore.data.remote.dto.CatalogItemDto
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class RatingsLoadTest {

    private val api = object : CatalogApi {
        override suspend fun getCatalog(): List<CatalogItemDto> = emptyList()

        override suspend fun getApp(id: String): AppDetailsDto = error("not used")
    }

    private val repository = GamesRepositoryImpl(api, GameMapper())

    @Test
    fun parallelRatingsFinishMuchSoonerThanSequential() = runBlocking {
        val ids = List(APP_COUNT) { index -> "app-$index" }

        var sequentialRatings = emptyMap<String, Float>()
        val sequentialMs = measureTimeMillis {
            sequentialRatings = repository.loadRatingsSequentially(ids)
        }
        var parallelRatings = emptyMap<String, Float>()
        val parallelMs = measureTimeMillis {
            parallelRatings = repository.loadRatingsInParallel(ids)
        }

        println("sequentialMs=$sequentialMs parallelMs=$parallelMs apps=$APP_COUNT")

        assertEquals(ids.toSet(), sequentialRatings.keys)
        assertEquals(ids.toSet(), parallelRatings.keys)
        assertTrue(sequentialRatings.values.all { rating -> rating in 3.0f..5.0f })
        assertTrue(parallelRatings.values.all { rating -> rating in 3.0f..5.0f })
        assertTrue(
            "sequential=$sequentialMs parallel=$parallelMs",
            sequentialMs >= APP_COUNT * 1_400L,
        )
        assertTrue(
            "sequential=$sequentialMs parallel=$parallelMs",
            parallelMs < 5_000L,
        )
        assertTrue(
            "sequential=$sequentialMs parallel=$parallelMs",
            sequentialMs > parallelMs * 3,
        )
    }

    @Test
    fun retrofitProxyCallsDefaultGetRating() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            val json = Json { ignoreUnknownKeys = true }
            val retrofitApi = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(CatalogApi::class.java)

            val rating = retrofitApi.getRating("app-1")

            assertEquals(0, server.requestCount)
            assertTrue(rating in 3.0f..5.0f)
        } finally {
            server.shutdown()
        }
    }

    private companion object {
        const val APP_COUNT = 12
    }
}

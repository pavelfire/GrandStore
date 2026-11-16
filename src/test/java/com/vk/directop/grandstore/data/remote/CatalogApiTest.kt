package com.vk.directop.grandstore.data.remote

import com.vk.directop.grandstore.applist.data.mapper.GameMapper
import com.vk.directop.grandstore.detailcard.data.mapper.AppDetailsMapper
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class CatalogApiTest {

    private val server = MockWebServer()
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        server.start()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getCatalogRequestsCatalogAndMapsFields() = runBlocking {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(
                    """
                    [{
                      "id": "1",
                      "name": "QuickNote",
                      "description": "notes",
                      "category": "Productivity",
                      "iconUrl": "https://example.com/icon.jpg"
                    }]
                    """.trimIndent(),
                ),
        )

        val items = api().getCatalog()

        assertEquals("/catalog", server.takeRequest().path)
        val game = GameMapper().toDomain(items.single())
        assertEquals("1", game.id)
        assertEquals("QuickNote", game.title)
        assertEquals("notes", game.description)
        assertEquals("Productivity", game.category)
        assertEquals("https://example.com/icon.jpg", game.image)
    }

    @Test
    fun getAppRequestsDetailsByIdAndMapsExtraFields() = runBlocking {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(
                    """
                    {
                      "id": "abc",
                      "name": "QuickNote",
                      "developer": "QuickSoft",
                      "category": "Productivity",
                      "ageRating": 3,
                      "size": 8.2,
                      "iconUrl": "https://example.com/icon.jpg",
                      "screenshotUrlList": [
                        "https://example.com/1.jpg",
                        "https://example.com/2.jpg"
                      ],
                      "description": "long description"
                    }
                    """.trimIndent(),
                ),
        )

        val dto = api().getApp("abc")

        assertEquals("/catalog/abc", server.takeRequest().path)
        val details = AppDetailsMapper().toDomain(dto)
        assertEquals("abc", details.id)
        assertEquals("QuickNote", details.name)
        assertEquals("QuickSoft", details.developer)
        assertEquals(3, details.ageRating)
        assertEquals(8.2, details.size, 0.001)
        assertEquals(listOf("https://example.com/1.jpg", "https://example.com/2.jpg"), details.screenshotUrls)
        assertEquals("long description", details.description)
    }

    private fun api(): CatalogApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        return retrofit.create(CatalogApi::class.java)
    }
}

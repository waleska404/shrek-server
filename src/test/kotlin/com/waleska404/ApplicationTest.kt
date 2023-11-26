package com.waleska404

import com.waleska404.di.koinModule
import com.waleska404.models.ApiResponse
import com.waleska404.repository.CharacterRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

class ApplicationTest {

    private val characterRepository: CharacterRepository by inject(CharacterRepository::class.java)

    @Before
    fun setup() {
        startKoin {
            modules(koinModule)
        }
    }

    @After
    fun close() {
        stopKoin()
    }

    @Test
    fun `access root endpoint, assert correct information`() = testApplication {
        val response = client.get("/")
        assertEquals(
            expected = HttpStatusCode.OK,
            actual = response.status
        )
        assertEquals(
            expected = "Welcome to Shrek API!",
            actual = response.bodyAsText()
        )
    }

    @Test
    fun `access all heroes endpoint, assert correct information`() = testApplication {
        val response = client.get("/shrek/characters")
        assertEquals(
            expected = HttpStatusCode.OK,
            actual = response.status
        )
        val expected = ApiResponse(
            success = true,
            message = "OK",
            prevPage = null,
            nextPage = 2,
            characters = characterRepository.page1
        )
        val actual = Json.decodeFromString<ApiResponse>(response.body())
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() =
        testApplication {
            val pages = 1..5
            val characters = listOf(
                characterRepository.page1,
                characterRepository.page2,
                characterRepository.page3,
                characterRepository.page4,
                characterRepository.page5
            )
            pages.forEach { page ->
                val response = client.get("/shrek/characters?page=$page")
                assertEquals(
                    expected = HttpStatusCode.OK,
                    actual = response.status
                )
                val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
                val expected = ApiResponse(
                    success = true,
                    message = "OK",
                    prevPage = calculatePrevPage(page = page),
                    nextPage = calculateNextPage(page = page),
                    characters = characters[page - 1],
                )
                assertEquals(
                    expected = expected,
                    actual = actual
                )
            }
        }

    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() =
        testApplication {
            val response = client.get("/shrek/characters?page=7")
            assertEquals(
                expected = HttpStatusCode.NotFound,
                actual = response.status
            )
            val expected = ApiResponse(
                success = false,
                message = "Characters not Found.",
            )
            val actual = Json.decodeFromString<ApiResponse>(response.body())
            assertEquals(
                expected = expected,
                actual = actual
            )
        }

    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() =
        testApplication {
            val response = client.get("/shrek/characters?page=invalid")
            assertEquals(
                expected = HttpStatusCode.BadRequest,
                actual = response.status
            )
            val expected = ApiResponse(
                success = false,
                message = "Only Numbers Allowed.",
            )
            val actual = Json.decodeFromString<ApiResponse>(response.body())
            assertEquals(
                expected = expected,
                actual = actual
            )
        }

    private fun calculateNextPage(page: Int) = if (page in 1..4) page + 1 else null
    private fun calculatePrevPage(page: Int) = if (page in 2..5) page - 1 else null
}

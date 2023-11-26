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
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

class ApplicationTest {

    private val characterRepository: CharacterRepository by inject(CharacterRepository::class.java)

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
        startKoin{
            modules(koinModule)
        }
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
}

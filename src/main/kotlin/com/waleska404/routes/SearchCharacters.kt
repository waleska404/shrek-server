package com.waleska404.routes

import com.waleska404.models.ApiResponse
import com.waleska404.repository.CharacterRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.lang.NumberFormatException

fun Route.searchCharacters() {
    val characterRepository: CharacterRepository by inject()

    get("/shrek/characters/search") {
        try {
            val name = call.request.queryParameters["name"]

            val apiResponse = characterRepository.searchCharacters(name = name)
            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only Numbers Allowed."),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}
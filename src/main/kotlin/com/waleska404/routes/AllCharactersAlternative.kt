package com.waleska404.routes

import com.waleska404.models.ApiResponse
import com.waleska404.repository.CharacterRepositoryAlternative
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.getAllCharactersAlternative() {
    val characterRepositoryAlternative: CharacterRepositoryAlternative by inject()

    get("/shrek/characters") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 3

            val apiResponse = characterRepositoryAlternative.getAllCharacters(
                page = page, limit = limit
            )
            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only Numbers Allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Characters not Found."),
                status = HttpStatusCode.NotFound
            )
        }
    }
}
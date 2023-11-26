package com.waleska404.plugins

import com.waleska404.models.ApiResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(
                ApiResponse(
                    success = false,
                    message = "Characters not Found."
                )
            )
        }
    }
}
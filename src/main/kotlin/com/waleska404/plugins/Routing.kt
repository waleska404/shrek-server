package com.waleska404.plugins

import com.waleska404.routes.getAllCharacters
import com.waleska404.routes.root
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllCharacters()
    }
}
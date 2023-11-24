package com.waleska404.plugins

import com.waleska404.routes.getAllCharacters
import com.waleska404.routes.root
import com.waleska404.routes.searchCharacters
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllCharacters()
        searchCharacters()

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}
package ru.rollsroll.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.rollsroll.cachs.InMemoryCache
import ru.rollsroll.cachs.TokenCache
import java.util.*

fun Application.configureLoginRouting() {

    routing {
        post("/login") {
    val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}
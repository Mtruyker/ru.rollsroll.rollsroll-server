package ru.rollsroll.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.rollsroll.database.tokens.TokenDTO
import ru.rollsroll.database.tokens.Tokens
import ru.rollsroll.database.users.UserDTO
import ru.rollsroll.database.users.UserModel
import ru.rollsroll.utils.isValidEmail
import java.util.*

class RegisterController (private val call: ApplicationCall) {

    suspend fun registerNewUser(){
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Адрес электронной почты недействителен")
        }
        val userDTO = UserModel.fetchUser(registerReceiveRemote.login)

        if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
        } else {
            val token = UUID.randomUUID().toString()

            try{
                UserModel.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        username = registerReceiveRemote.username
                    )
                )
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
            }

            Tokens.insert(TokenDTO(
                rowId = UUID.randomUUID().toString(), login = registerReceiveRemote.login,
                token = token
            )
            )
            call.respond(RegisterResponseRemote(token=token))
        }
    }
}


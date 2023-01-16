package ru.rollsroll.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.rollsroll.database.tokens.TokenDTO
import ru.rollsroll.database.tokens.Tokens
import ru.rollsroll.database.users.UserModel
import java.util.*

class LoginController (private val call: ApplicationCall) {

    suspend fun performLogin(){
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = UserModel.fetchUser(receive.login)

        if(userDTO == null){
            call.respond(HttpStatusCode.BadRequest, "Пользователь не найден")
        } else {
            if (userDTO.password == receive.password){
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                    rowId = UUID.randomUUID().toString(), login = receive.login,
                    token = token
                )
                )
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Неверный пароль")
            }
        }
    }
}
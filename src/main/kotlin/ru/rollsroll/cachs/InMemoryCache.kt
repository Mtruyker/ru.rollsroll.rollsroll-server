package ru.rollsroll.cachs

import ru.rollsroll.features.register.RegisterReceiveRemote
import sun.security.ec.point.ProjectivePoint.Mutable

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache{
    val userlist: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}
package org.ua.wohnung.bot.dto

enum class Role {
    OWNER, ADMIN, USER
}

data class AccountDto(
    val login: String,
    val role: Role = Role.USER
)

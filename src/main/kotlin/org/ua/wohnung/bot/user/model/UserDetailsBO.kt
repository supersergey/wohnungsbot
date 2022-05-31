package org.ua.wohnung.bot.user.model

data class UserDetailsBO(
    val username: String,
    val phone: String,
    val numberOfTenants: Int,
    val pets: Boolean,
    val bundesLand: BundesLand
)

package org.ua.wohnung.bot.exception

sealed class WohnungsBotException(message: String, cause: Throwable? = null) : Throwable(message, cause) {
    class UserNotFoundException(val userId: String) :
        WohnungsBotException("User not found: $userId")
}

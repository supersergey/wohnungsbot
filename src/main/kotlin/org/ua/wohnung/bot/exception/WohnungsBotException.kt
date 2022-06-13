package org.ua.wohnung.bot.exception

abstract class WohnungsBotException(message: String, cause: Throwable? = null) : Throwable(message, cause)

sealed class ServiceException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class UserNotFoundException(val userId: Int) : ServiceException("User not found: $userId")
}

sealed class UserInputValidationException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class InvalidBundesLand(val bundesLand: String) : UserInputValidationException("Bundesland not found: $bundesLand")
    class InvalidPhoneNumber(val phoneNumber: String) : UserInputValidationException("Invalid phone number: $phoneNumber")
    class InvalidUserName(val userName: String) : UserInputValidationException("Invalid user name: $userName")
}

sealed class SheetValidationException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class InvalidApartmentId(val id: String) : SheetValidationException("Invalid apartment id: $id")
    class InvalidBunderLand(val bundesLand: String) : SheetValidationException("Invalid Bundesland: $bundesLand")
    class InvalidTenantsCount(val tenantsCount: String) : SheetValidationException("Invalid tenantsCount: $tenantsCount")
}

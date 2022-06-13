package org.ua.wohnung.bot.exception

import org.ua.wohnung.bot.persistence.generated.enums.Role

abstract class WohnungsBotException(message: String, cause: Throwable? = null) : Throwable(message, cause)

sealed class ServiceException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class UserNotFoundException(val userId: Int) : ServiceException("User not found: $userId")
    class AccessViolationException(val userId: Int, actualRole: Role?, vararg expectedRole: Role)
        : ServiceException("User $userId should have $expectedRole, but actual was $actualRole. Operation denied")
}

sealed class UserInputValidationException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class InvalidBundesLand(val bundesLand: String) : UserInputValidationException("Bundesland not found: $bundesLand")
    class InvalidPhoneNumber(val phoneNumber: String) : UserInputValidationException("Invalid phone number: $phoneNumber")
    class InvalidUserName(val userName: String) : UserInputValidationException("Invalid user name: $userName")
    class InvalidUserId(input: String) : UserInputValidationException("User id not found: $input")
}

sealed class SheetValidationException(message: String, cause: Throwable? = null) : WohnungsBotException(message, cause) {
    class InvalidApartmentId(val id: String) : SheetValidationException("Invalid apartment id: $id")
    class InvalidBundesLand(val bundesLand: String, val rowId: String) : SheetValidationException("Invalid Bundesland: $bundesLand, rowId: $rowId")
    class InvalidTenantsCount(val tenantsCount: String) : SheetValidationException("Invalid tenantsCount: $tenantsCount")
}

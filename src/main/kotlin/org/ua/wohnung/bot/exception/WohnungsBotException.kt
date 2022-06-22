package org.ua.wohnung.bot.exception

import org.ua.wohnung.bot.persistence.generated.enums.Role

abstract class WohnungsBotException(message: String, cause: Throwable? = null) : Throwable(message, cause)

sealed class ServiceException(message: String, val userMessage: String = "", val finishSession: Boolean = true, cause: Throwable? = null) :
    WohnungsBotException(message, cause) {
    class UnreadableMessage(updateId: Int) : ServiceException("Message unreadable, $updateId")
    class UserNotFound(val userId: Int) : ServiceException("User not found: $userId")
    class ApartmentNotFound(apartmentId: String) :
        ServiceException("Apartment not found: $apartmentId", "Помешкання не знайдено")

    class AccessViolation(val userId: Int, actualRole: Role?, vararg expectedRole: Role) :
        ServiceException(
            "User $userId should have $expectedRole, but actual was $actualRole. Operation denied",
            "Доступ заборонено"
        )

    class UserApplicationRateExceeded(userId: Int, maxAttempts: Int) : ServiceException(
        "Too many application attempts, userId: $userId",
        "Протягом доби можна подати заявку на $maxAttempts помешкань. Спробуйте пізніше"
    )
}

sealed class UserInputValidationException(message: String, cause: Throwable? = null) :
    WohnungsBotException(message, cause) {
    class InvalidBundesLand(val bundesLand: String) : UserInputValidationException("Bundesland not found: $bundesLand")
    class InvalidPhoneNumber(val phoneNumber: String) :
        UserInputValidationException("Invalid phone number: $phoneNumber")

    class InvalidUserName(val userName: String) : UserInputValidationException("Invalid user name: $userName")
    class InvalidUserId(input: String) : UserInputValidationException("User id not found: $input")
}

sealed class SheetValidationException(message: String, cause: Throwable? = null) :
    WohnungsBotException(message, cause) {
    class InvalidApartmentId(val id: String) : SheetValidationException("Invalid apartment id: $id")
    class InvalidBundesLand(val bundesLand: String, val rowId: String) :
        SheetValidationException("Invalid Bundesland: $bundesLand, rowId: $rowId")
}

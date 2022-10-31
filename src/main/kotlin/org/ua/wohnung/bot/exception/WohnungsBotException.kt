package org.ua.wohnung.bot.exception

import org.ua.wohnung.bot.persistence.generated.enums.Role

abstract class WohnungsBotException(message: String, open val userMessage: String = "", cause: Throwable? = null) :
    Throwable(message, cause)

sealed class ServiceException(
    message: String,
    override val userMessage: String = "❌ Помилка системи. Ми спробуємо її полагодити. Спробуйте повернутися до Бота через деякий час",
    val finishSession: Boolean = true,
    cause: Throwable? = null
) : WohnungsBotException(message, userMessage, cause) {

    class UnreadableMessage(updateId: Int) : ServiceException("Message unreadable, $updateId")
    class UserNotFound(val userId: Long) :
        ServiceException("User not found: $userId", "Користувач не знайдений: $userId")

    class UsernameNotFound(val userId: Long) :
        ServiceException(
            message = "Username not found: $userId",
            userMessage =
            """
                ❌ Помилка! Налаштуйте свій псевдонім (username) в Телеграмі і спробуйте знову. Дивіться інструкцію тут: 
                
                🍏Для Iphone (IOS):  https://www.youtube.com/shorts/Md79GzTsZn0

                🤖Для Android:  https://youtu.be/AJCzF7sPoI0

                💻Для комп'ютерів/ноутбуків на Windows: https://www.youtube.com/watch?v=Q4AUj84oDlA

                🖥Для комп'ютерів/ноутбуків від Apple: https://www.youtube.com/watch?v=XfqBIcoK2Yk
            """.trimIndent()
        )

    class AccessViolation(val userId: Long, actualRole: Role?, vararg expectedRole: Role) :
        ServiceException(
            "User $userId should have $expectedRole, but actual was $actualRole. Operation denied",
            "Доступ заборонено"
        )
}

sealed class SheetValidationException(message: String, cause: Throwable? = null) :
    WohnungsBotException(message, "", cause) {
    class InvalidApartmentId(val id: String) : SheetValidationException("Invalid apartment id: $id")
    class InvalidBundesLand(bundesLand: String, rowId: String) :
        SheetValidationException("Invalid Bundesland: $bundesLand, rowId: $rowId")
}

package org.ua.wohnung.bot.exception

import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role

abstract class WohnungsBotException(message: String, open val userMessage: String = "", cause: Throwable? = null) :
    Throwable(message, cause)

sealed class ServiceException(
    message: String,
    override val userMessage: String = "❌ Помилка системи. Ми спробуємо її полагодити. Спробуйте повернутися до Бота через деякий час",
    val finishSession: Boolean = true,
    cause: Throwable? = null
) : WohnungsBotException(message, userMessage, cause) {

    class UnexpectedInputException(flowStep: FlowStep) :
        ServiceException(
            message = "Unexpected input exception, step: $flowStep",
            userMessage = "❌ Неправильно введені дані. Якшо бажаєте повернутись на початок, натисніть /start"
        )

    class StepFactoryNotFound(flowStep: FlowStep) :
        ServiceException("Step factory not found, flowStep: $flowStep")

    class StepProcessorNotFound(updateId: Int, flowStep: FlowStep, userInput: String) :
        ServiceException("Step processor not found, flowStep: $flowStep, userInput: $userInput, updateId: $updateId")

    class UnreadableMessage(updateId: Int) : ServiceException("Message unreadable, $updateId")
    class UserNotFound(val userId: Long) :
        ServiceException("User not found: $userId", "Користувач не знайдений: $userId")

    class ApartmentNotFound(apartmentId: String) :
        ServiceException("Apartment not found: $apartmentId", "Помешкання не знайдено: $apartmentId")

    class UsernameNotFound(val userId: Long) :
        ServiceException(
            message = "Username not found: $userId",
            userMessage = "❌ Помилка! Налаштуйте свій псевдонім (username) в Телеграмі і спробуйте знову. Дивіться інструкцію тут: https://youtube.com/shorts/Md79GzTsZn0",
        )

    class AccessViolation(val userId: Long, actualRole: Role?, vararg expectedRole: Role) :
        ServiceException(
            "User $userId should have $expectedRole, but actual was $actualRole. Operation denied",
            "Доступ заборонено"
        )

    class UserApplicationRateExceeded(userId: Long, maxAttempts: Int) : ServiceException(
        "Too many application attempts, userId: $userId",
        "Протягом доби можна подати заявку на $maxAttempts помешкань. Спробуйте пізніше"
    )

    object MatchingApartmentNotFoundException : ServiceException(
        message = "Suitable apartments not found",
        userMessage = "\uD83C\uDFE0 На жаль, наразі ми не маємо пропозицій, які вам підходять. Якщо щось з'явиться, ви отримаєте повідомлення."
    )
}

sealed class UserInputValidationException(message: String, userMessage: String = "", cause: Throwable? = null) :
    WohnungsBotException(message, userMessage, cause) {
    class InvalidBundesLand(val bundesLand: String) : UserInputValidationException(
        "Bundesland not found: $bundesLand",
        "Такої Федеральної Землі немає: $bundesLand. Виберіть землю, натиснувши кнопку внизу. Не друкуйте назву вручну!"
    )

    class InvalidPhoneNumber(val phoneNumber: String) :
        UserInputValidationException(
            "Invalid phone number: $phoneNumber",
            "Цей номер $phoneNumber неправльний, введіть номер правильно"
        )

    class InvalidUserName(val userName: String) : UserInputValidationException("Invalid user name: $userName")
    class InvalidUserId(input: String) : UserInputValidationException("User id not found: $input")

    class InvalidFamilyCount(val count: String) : UserInputValidationException(
        "Invalid family member count: $count",
        "Неправильне значення: $count. Виберіть кількість членів родини, натиснувши кнопку внизу. Не друкуйте значення вручну!"
    )
}

sealed class SheetValidationException(message: String, cause: Throwable? = null) :
    WohnungsBotException(message, "", cause) {
    class InvalidApartmentId(val id: String) : SheetValidationException("Invalid apartment id: $id")
    class InvalidBundesLand(val bundesLand: String, val rowId: String) :
        SheetValidationException("Invalid Bundesland: $bundesLand, rowId: $rowId")
}

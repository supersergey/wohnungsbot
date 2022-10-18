package org.ua.wohnung.bot.flows.registereduser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

abstract class RegisteredUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractUserInputProcessor(userService, messageSource) {
    abstract fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput?

    override fun processGenericCommands(chatMetadata: ChatMetadata): StepOutput? {
        return getErrorIfRegistrationIsNotComplete(chatMetadata)?.let {
            when (chatMetadata.input) {
                "/start" -> processStartCommand(chatMetadata)
                else -> processSpecificCommands(chatMetadata)
            }
        }
    }

    private fun getErrorIfRegistrationIsNotComplete(chatMetadata: ChatMetadata): StepOutput? {
        return if (userService.isRegistrationComplete(chatMetadata.userId)) {
            StepOutput.Error(
                message = "❌ Спочатку пройдіть реєстрацію. Для цього натисніть /start, а далі кнопку *Зарєеструватись*",
                finishSession = true
            )
        } else
            null
    }

    private fun processStartCommand(chatMetadata: ChatMetadata): StepOutput {
        val user = userService.findById(chatMetadata.userId)
        return StepOutput.InlineButtons(
            message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                    .format(user?.firstLastName?.capitalize()),
            nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            replyOptions = listOf("Переглянути наявне житло")
        )
    }

    private fun String?.capitalize(): String {
        return this?.split("\\s".toRegex())
            ?.joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
            ?: ""
    }
}
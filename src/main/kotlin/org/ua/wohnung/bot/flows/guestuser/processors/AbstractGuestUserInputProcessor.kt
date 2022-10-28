package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

abstract class AbstractGuestUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractUserInputProcessor(userService, messageSource) {

    abstract fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput?

    override fun processGenericCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when(chatMetadata.input) {
            "/start" -> processStartCommand()
            else -> processSpecificCommands(chatMetadata)
        }
    }

    private fun processStartCommand(): StepOutput {
        return StepOutput.InlineButtons(
            message = messageSource[FlowStep.CONVERSATION_START],
            nextStep = FlowStep.CONVERSATION_START,
            replyOptions = listOf("Зареєструватись"),
            finishSession = true
        )
    }
}
package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class InitInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FlowStep.INITIAL

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.InlineButtons(
            message = Message(messageSource[FlowStep.CONVERSATION_START]),
            nextStep = FlowStep.CONVERSATION_START,
            replyOptions = listOf("Зареєструватись")
        )
    }
}
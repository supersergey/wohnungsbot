package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.ACCEPT_POLICIES
import org.ua.wohnung.bot.flows.step.FlowStep.CONVERSATION_START
import org.ua.wohnung.bot.user.UserService

class ConversationStartInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = CONVERSATION_START

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "зареєструватись" -> StepOutput.InlineButtons(
                message = messageSource[ACCEPT_POLICIES],
                nextStep = ACCEPT_POLICIES,
                replyOptions = listOf("Так", "Ні"),
            )
            else -> null
        }
    }
}

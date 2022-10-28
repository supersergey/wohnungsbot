package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.ACCEPT_POLICIES
import org.ua.wohnung.bot.flows.step.FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL
import org.ua.wohnung.bot.user.UserService

class AcceptPoliciesUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = ACCEPT_POLICIES

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "так" -> StepOutput.InlineButtons(
                message = messageSource[PERSONAL_DATA_PROCESSING_APPROVAL],
                nextStep = PERSONAL_DATA_PROCESSING_APPROVAL,
                replyOptions = listOf("Так", "Ні"),
                editMessage = true
            )
            "ні" -> StepOutput.Error(
                message = messageSource[FlowStep.CONVERSATION_FINISHED_DECLINED],
                finishSession = true
            )
            else -> null
        }
    }
}

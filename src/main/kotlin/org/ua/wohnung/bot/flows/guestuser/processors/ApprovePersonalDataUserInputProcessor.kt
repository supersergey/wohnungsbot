package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.CONVERSATION_FINISHED_DECLINED
import org.ua.wohnung.bot.flows.step.FlowStep.GERMAN_REGISTRATION
import org.ua.wohnung.bot.flows.step.FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL
import org.ua.wohnung.bot.user.UserService

class ApprovePersonalDataUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = PERSONAL_DATA_PROCESSING_APPROVAL

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "так" -> StepOutput.InlineButtons(
                message = messageSource[GERMAN_REGISTRATION],
                nextStep = GERMAN_REGISTRATION,
                replyOptions = listOf("Так", "Ні"),
                editMessage = true
            )
            "ні" -> StepOutput.PlainText(
                message = messageSource[CONVERSATION_FINISHED_DECLINED],
                finishSession = true
            )
            else -> null
        }
    }
}

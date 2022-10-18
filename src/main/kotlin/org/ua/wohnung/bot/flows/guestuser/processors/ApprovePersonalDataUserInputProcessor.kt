package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.BUNDESLAND_SELECTION
import org.ua.wohnung.bot.flows.step.FlowStep.CONVERSATION_FINISHED_DECLINED
import org.ua.wohnung.bot.flows.step.FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

class ApprovePersonalDataUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = PERSONAL_DATA_PROCESSING_APPROVAL

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "так" -> StepOutput.InlineButtons(
                message = messageSource[BUNDESLAND_SELECTION],
                nextStep = BUNDESLAND_SELECTION,
                replyOptions = BundesLand.values().map { it.germanName },
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

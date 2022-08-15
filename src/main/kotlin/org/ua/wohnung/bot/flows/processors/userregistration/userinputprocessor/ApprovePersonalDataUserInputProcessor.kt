package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep

class ApprovePersonalDataUserInputProcessor(private val messageSource: MessageSource) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        return when (chatMetadata.input) {
            "так" -> UserInputProcessingResult.Success
            "ні" -> UserInputProcessingResult.Error(
                message = messageSource[FlowStep.CONVERSATION_FINISHED_DECLINED],
                finishSession = true
            )
            else -> null
        }
    }
}

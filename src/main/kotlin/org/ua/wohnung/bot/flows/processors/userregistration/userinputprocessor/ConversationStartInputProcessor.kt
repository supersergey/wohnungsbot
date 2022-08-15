package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep

class ConversationStartInputProcessor : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.CONVERSATION_START

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        return when (chatMetadata.input) {
            "зареєструватись" -> UserInputProcessingResult.Success
            else -> null
        }
    }
}

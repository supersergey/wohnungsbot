package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep

interface UserInputProcessor : (ChatMetadata) -> UserInputProcessingResult {
    val supportedStep: FlowStep
}

sealed class UserInputProcessingResult {
    object Success: UserInputProcessingResult()
    class Error(val message: String, val finishSession: Boolean): UserInputProcessingResult()
}

sealed class StepOutput(open val message: Message, open val nextStep: FlowStep? = null) {
    data class PlainText(override val message: Message, override val nextStep: FlowStep) :
        StepOutput(message, nextStep)

    data class MarkupButtons(
        override val message: Message,
        override val nextStep: FlowStep,
        val replyOptions: List<String>
    ) :
        StepOutput(message, nextStep)

    data class InlineButtons(
        override val message: Message,
        override val nextStep: FlowStep,
        val replyOptions: List<String>,
        val isEditMessage: Boolean = false
    ) :
        StepOutput(message)

    data class Error(override val message: Message, val finishSession: Boolean = true) :
        StepOutput(message)
}

data class Message(val payload: String, val id: String = "")

data class ReplyPayload(
    val nextStep: FlowStep? = null,
    val description: String? = null
)

data class UserInput(val text: String, val meta: String? = null)

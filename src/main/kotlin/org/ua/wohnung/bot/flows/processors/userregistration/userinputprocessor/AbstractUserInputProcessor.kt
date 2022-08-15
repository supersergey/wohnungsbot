package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep

abstract class AbstractUserInputProcessor : (ChatMetadata) -> UserInputProcessingResult {
    abstract val supportedStep: FlowStep
    abstract fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult?

    override operator fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        val processingResult = when (chatMetadata.input) {
            in DEFAULT_INPUTS -> UserInputProcessingResult.Default
            else -> doInvoke(chatMetadata)
        }
        return processingResult ?: UserInputProcessingResult.Error(
            message = "❌ Неправильно введені дані. Будь-ласка, надайте відповідь на питання. Якшо бажаєте повернутись на початок, натисніть /start",
            finishSession = false
        )
    }

    private companion object {
        private val DEFAULT_INPUTS = setOf("/start", "/site", "/conditions", "/list_apartments")
    }
}

sealed class UserInputProcessingResult {
    object Success : UserInputProcessingResult()
    class Error(val message: String, val finishSession: Boolean) : UserInputProcessingResult()
    object Default : UserInputProcessingResult()
}

sealed class StepOutput(
    open val message: Message,
    open val nextStep: FlowStep? = null,
    open val finishSession: Boolean = false
) {
    data class PlainText(
        override val message: Message,
        override val nextStep: FlowStep,
        override val finishSession: Boolean = false
    ) :
        StepOutput(message = message, nextStep = nextStep, finishSession = finishSession)

    data class MarkupButtons(
        override val message: Message,
        override val nextStep: FlowStep,
        val replyOptions: List<String>,
        override val finishSession: Boolean = false
    ) :
        StepOutput(message = message, nextStep = nextStep, finishSession = finishSession)

    data class InlineButtons(
        override val message: Message,
        override val nextStep: FlowStep,
        val replyOptions: List<String>,
        val isEditMessage: Boolean = false,
        override val finishSession: Boolean = false
    ) :
        StepOutput(message = message, nextStep = nextStep, finishSession = finishSession)

    data class Error(override val message: Message, override val finishSession: Boolean = true) :
        StepOutput(message = message, finishSession = finishSession)
}

data class Message(val payload: String, val id: String = "")

data class ReplyPayload(
    val nextStep: FlowStep? = null,
    val description: String? = null
)

data class UserInput(val text: String, val meta: String? = null)

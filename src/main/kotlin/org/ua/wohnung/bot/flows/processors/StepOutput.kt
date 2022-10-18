package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.step.FlowStep

sealed class StepOutput(
    open val message: String,
    open val nextStep: FlowStep? = null,
    open val editMessage: Boolean = false,
    open val finishSession: Boolean = false
) {
    data class PlainText(
        override val message: String,
        override val nextStep: FlowStep? = null,
        override val editMessage: Boolean = false,
        override val finishSession: Boolean = false
    ) :
        StepOutput(message = message, nextStep = nextStep, finishSession = finishSession)

    data class MarkupButtons(
        override val message: String,
        override val nextStep: FlowStep,
        val replyOptions: List<String>,
    ) :
        StepOutput(message = message)

    data class InlineButtons(
        override val message: String,
        override val nextStep: FlowStep,
        val replyOptions: List<String>,
        val replyMetaData: List<String>? = null,
        override val editMessage: Boolean = false,
        override val finishSession: Boolean = false
    ) :
        StepOutput(message = message, nextStep = nextStep, finishSession = finishSession)

    data class Error(override val message: String, override val finishSession: Boolean = true) :
        StepOutput(message = message, finishSession = finishSession)
}

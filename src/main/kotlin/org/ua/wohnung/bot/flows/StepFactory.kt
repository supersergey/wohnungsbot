package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor
import org.ua.wohnung.bot.flows.processors.ProcessorContainer

sealed class Step(
    open val id: FlowStep,
    open val caption: String,
    open val reply: Reply,
    open val preProcessor: PreProcessor,
    open val postProcessor: PostProcessor,
) {
    class General internal constructor(
        override val id: FlowStep,
        override val caption: String,
        override val reply: Reply,
        override val preProcessor: PreProcessor,
        override val postProcessor: PostProcessor,
    ) : Step(id, caption, reply, preProcessor, postProcessor)

    class Termination internal constructor(
        override val id: FlowStep,
        override val caption: String,
        override val preProcessor: PreProcessor,
        override val postProcessor: PostProcessor,
    ) : Step(id, caption, Reply.SingleText(null), preProcessor, postProcessor)
}

sealed class Reply(val options: Map<String, ReplyOption>) {
    class WithButtons(vararg options: ReplyOption) : Reply(options.associateBy { it.command })
    class WithInlineButtons(vararg options: ReplyOption) : Reply(options.associateBy { it.command })
    class MultiText(vararg options: ReplyOption) :
        Reply(
            options.associateBy { it.command }
        )
    class SingleText(nextStep: FlowStep?) : Reply(mapOf(ANY_ANSWER_ACCEPTED to ReplyOption(ANY_ANSWER_ACCEPTED, nextStep)))

    companion object {
        const val ANY_ANSWER_ACCEPTED = "ANY_ANSWER_ACCEPTED"
    }
}

class StepFactory(
    private val messageSource: MessageSource,
    private val preProcessors: ProcessorContainer.PreProcessors,
    private val postProcessors: ProcessorContainer.PostProcessors
) {
    fun singleReply(
        id: FlowStep,
        next: FlowStep
    ): Step.General =
        Step.General(id, messageSource[id], Reply.SingleText(next), preProcessors[id], postProcessors[id])

    fun multipleButtons(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.WithButtons(*replies), preProcessors[id], postProcessors[id])

    fun multipleInlineButtons(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.WithInlineButtons(*replies), preProcessors[id], postProcessors[id])

    fun multipleTextOptions(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.MultiText(*replies), preProcessors[id], postProcessors[id])

    fun termination(
        id: FlowStep,
    ): Step.Termination =
        Step.Termination(id, messageSource[id], preProcessors[id], postProcessors[id])
}

data class ReplyOption(val command: String, val flowStep: FlowStep?, val description: String = "")

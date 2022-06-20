package org.ua.wohnung.bot.flows.step

import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor

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

sealed class Reply(val options: () -> Map<String, ReplyOption>) {
    class WithButtons(vararg options: ReplyOption) : Reply({ options.associateBy { it.command } })
    class WithInlineButtons(vararg options: ReplyOption) : Reply({ options.associateBy { it.command } })
    class WithDynamicButtons(block: () -> Map<String, ReplyOption>) : Reply(block)
    class MultiText(vararg options: ReplyOption) :
        Reply(
            { options.associateBy { it.command } }
        )
    class SingleText(nextStep: FlowStep?) : Reply(
        { mapOf(ANY_ANSWER_ACCEPTED to ReplyOption(ANY_ANSWER_ACCEPTED, nextStep)) }
    )

    companion object {
        const val ANY_ANSWER_ACCEPTED = "ANY_ANSWER_ACCEPTED"
    }
}

data class ReplyOption(val command: String, val flowStep: FlowStep?, val description: String = "")

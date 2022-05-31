package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor

sealed class Step(
    open val id: String,
    open val caption: String,
    open val reply: Reply,
    open val preProcessor: PreProcessor,
    open val postProcessor: PostProcessor,
) {
    class General internal constructor(
        override val id: String,
        override val caption: String,
        override val reply: Reply,
        override val preProcessor: PreProcessor,
        override val postProcessor: PostProcessor,
    ) : Step(id, caption, reply, preProcessor, postProcessor)

    class Termination internal constructor(
        override val id: String,
        override val caption: String,
        override val preProcessor: PreProcessor,
        override val postProcessor: PostProcessor,
    ) : Step(id, caption, Reply.Custom(null), preProcessor, postProcessor)
}

sealed class Reply(val options: Map<String, String?>) {
    class Inline(vararg options: Pair<String, String?>) : Reply(options.toMap())
    class Custom(nextStep: String?) : Reply(mapOf(ANY_ANSWER_ACCEPTED to nextStep))

    companion object {
        const val ANY_ANSWER_ACCEPTED = "ANY_ANSWER_ACCEPTED"
    }
}

class StepFactory(private val messageSource: MessageSource) {
    fun singleReply(
        id: String,
        next: String,
        preProcessor: PreProcessor = PreProcessor.Empty(id),
        postProcessor: PostProcessor = PostProcessor.Empty(id)
    ): Step.General =
        Step.General(id, messageSource[id], Reply.Custom(next), preProcessor, postProcessor)

    fun multipleReplies(
        id: String,
        vararg replies: Pair<String, String>,
        preProcessor: PreProcessor = PreProcessor.Empty(id),
        postProcessor: PostProcessor = PostProcessor.Empty(id)
    ): Step.General =
        Step.General(id, messageSource[id], Reply.Inline(*replies), preProcessor, postProcessor)

    fun termination(
        id: String,
        preProcessor: PreProcessor = PreProcessor.Empty(id),
        postProcessor: PostProcessor = PostProcessor.Empty(id)
    ): Step.Termination =
        Step.Termination(id, messageSource[id], preProcessor, postProcessor)
}

package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.configuration.MessageSource

sealed class Step(
    open val id: String,
    open val caption: String,
    open val reply: Reply
) {
    class General internal constructor(
        override val id: String,
        override val caption: String,
        override val reply: Reply,
    ) : Step(id, caption, reply)

    class Termination internal constructor(
        override val id: String,
        override val caption: String
        ) : Step(id, caption, Reply.Custom(null))
}

sealed class Reply(val options: Map<String, String?>) {
    class Inline(vararg options: Pair<String, String?>) : Reply(options.toMap())
    class Custom(nextStep: String?) : Reply(mapOf(ANY_ANSWER_ACCEPTED to nextStep))

    companion object {
        const val ANY_ANSWER_ACCEPTED = "ANY_ANSWER_ACCEPTED"
    }
}

class StepFactory(private val messageSource: MessageSource, private val flow: Flow) {
    fun singleReply(id: String, next: String): Step.General =
            Step.General(id, messageSource[id], Reply.Custom(next)).add()

    fun multipleReplies(id: String, vararg replies: Pair<String, String>): Step.General =
            Step.General(id, messageSource[id], Reply.Inline(*replies)).add()

    fun termination(id: String): Step.Termination =
        Step.Termination(id, messageSource[id]).add()

    private fun <T: Step> T.add(): T {
        flow.add(this)
        return this
    }
}

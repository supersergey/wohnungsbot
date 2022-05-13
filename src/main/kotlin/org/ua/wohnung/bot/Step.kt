package org.ua.wohnung.bot

sealed class Step(
    open val caption: String,
    open val reply: Reply
) {
    data class General(
        override val caption: String,
        override val reply: Reply,
    ) : Step(caption, reply)

    data class Termination(override val caption: String) : Step(caption, Reply.Generic(caption, null))
}

sealed class Reply(val options: Map<String, Step?>) {
    class Inline(vararg options: Pair<String, Step?>) :
        Reply(options.toMap())

    class Generic(text: String, step: Step?) : Reply(mapOf(text to step))
}

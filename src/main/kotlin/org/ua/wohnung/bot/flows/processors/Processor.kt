package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep

interface Processor {
    operator fun invoke(username: String, input: String): Any
    val stepId: FlowStep

    object Empty : Processor {
        override val stepId = FlowStep.CONVERSATION_START
        override operator fun invoke(username: String, input: String) {}
    }
}

interface PreProcessor : Processor {
    object Empty: PreProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(username: String, input: String) {}
    }
}
interface PostProcessor : Processor {
    object Empty: PostProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(username: String, input: String) {}
    }
}

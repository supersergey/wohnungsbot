package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

interface Processor {
    operator fun invoke(account: Account, input: String): Any
    val stepId: FlowStep

    object Empty : Processor {
        override val stepId = FlowStep.CONVERSATION_START
        override operator fun invoke(account: Account, input: String) {}
    }
}

interface PreProcessor : Processor {
    object Empty : PreProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(account: Account, input: String) {}
    }
}
interface PostProcessor : Processor {
    object Empty : PostProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(account: Account, input: String) {}
    }
}

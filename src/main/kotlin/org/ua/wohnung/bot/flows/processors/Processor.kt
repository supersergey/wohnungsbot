package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

interface Processor {
    val stepId: FlowStep
    operator fun invoke(account: Account, input: String): Any

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

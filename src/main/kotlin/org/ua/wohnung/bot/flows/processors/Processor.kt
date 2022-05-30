package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.Session
import org.ua.wohnung.bot.persistence.AccountRepository

interface Processor : (String) -> Any {
    val stepId: String
}

interface PreProcessor : Processor {
    class Empty(override val stepId: String) : PreProcessor {
        override fun invoke(p1: String) {}
    }
}
interface PostProcessor : Processor {
    class Empty(override val stepId: String) : PostProcessor {
        override fun invoke(p1: String) {}
    }
}

class PersonalDataRemovalStepPreProcessor(
    private val accountRepository: AccountRepository,
    private val session: Session
) : PreProcessor {
    override val stepId = "conversation_finish_removal"

    override fun invoke(userId: String) {
        accountRepository.deleteByLogin(userId)
    }
}

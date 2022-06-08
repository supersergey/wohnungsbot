package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class MessagePreProcessor : PreProcessor {
    class RegisteredUserConversationStartPreProcessor(private val userService: UserService) : MessagePreProcessor() {
        override val stepId = FlowStep.REGISTERED_USER_CONVERSATION_START

        override fun invoke(account: Account, input: String): String {
            return input.format(userService.findById(account.id)?.firstLastName ?: "Невідомий")
        }
    }

    object Empty : MessagePreProcessor() {
        override fun invoke(account: Account, input: String): String { return input }

        override val stepId: FlowStep = FlowStep.CONVERSATION_START
    }
}

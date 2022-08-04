package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.PreProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class UserDetailsPreProcessor : PreProcessor {
    class BundesLandSelectionPreProcessor(private val userService: UserService) : UserDetailsPreProcessor() {
        override val stepId = FlowStep.BUNDESLAND_SELECTION

        override fun invoke(chatMetadata: ChatMetadata, input: String) {
            if (chatMetadata.username.isNullOrBlank()) {
                throw ServiceException.UsernameNotFound(chatMetadata.userId)
            }
            userService.createAccount(chatMetadata.toAccount().apply { role = Role.USER })
        }

        private fun ChatMetadata.toAccount(): Account =
            Account(userId, chatId, username, null)
    }

    class UserRegistrationFlowConditionsRejectedPreProcessor(private val userService: UserService) : UserDetailsPreProcessor() {
        override val stepId = FlowStep.CONVERSATION_FINISH_REMOVAL

        override fun invoke(chatMetadata: ChatMetadata, input: String) {
            userService.delete(chatMetadata.userId)
        }
    }
}

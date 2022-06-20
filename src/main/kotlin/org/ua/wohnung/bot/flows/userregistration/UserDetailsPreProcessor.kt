package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.flows.processors.PreProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class UserDetailsPreProcessor : PreProcessor {
    class BundesLandSelectionPreProcessor(private val userService: UserService) : UserDetailsPreProcessor() {
        override val stepId = FlowStep.BUNDESLAND_SELECTION

        override fun invoke(account: Account, input: String) {
            userService.createAccount(account.apply { role = Role.USER })
        }
    }

    class UserRegistrationFlowConditionsRejectedPreProcessor(private val userService: UserService) : UserDetailsPreProcessor() {
        override val stepId = FlowStep.CONVERSATION_FINISH_REMOVAL

        override fun invoke(account: Account, input: String) {
            userService.delete(account.id)
        }
    }
}

package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class RegisteredUserPreProcessor : PreProcessor {
    class UserRegistrationFlowConditionsRejectedPreProcessor(private val userService: UserService) :
        UserDetailsPreProcessor() {
        override val stepId = FlowStep.CONVERSATION_FINISH_REMOVAL

        override fun invoke(account: Account, input: String) {
            userService.delete(account.id)
        }
    }
}

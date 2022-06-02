package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class UserDetailsPreProcessor : PreProcessor {
    class BundesLandSelectionPreProcessor(private val userService: UserService) : UserDetailsPreProcessor() {
        override val stepId = FlowStep.BUNDESLAND_SELECTION

        override fun invoke(username: String, input: String) {
            userService.createAccount(
                Account(username, Role.USER)
            )
        }
    }
}

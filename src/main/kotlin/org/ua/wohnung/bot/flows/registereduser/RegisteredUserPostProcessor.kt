package org.ua.wohnung.bot.flows.registereduser

import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class RegisteredUserPostProcessor : PostProcessor {
    class ApplyForApartment(private val userService: UserService) : RegisteredUserPostProcessor() {
        override val stepId = FlowStep.REGISTERED_USER_APPLY_FOR_APARTMENT

        override fun invoke(account: Account, input: String) {
            userService.findUserRoleById(account.id) ?: throw ServiceException.UserNotFound(account.id)
        }
    }
}

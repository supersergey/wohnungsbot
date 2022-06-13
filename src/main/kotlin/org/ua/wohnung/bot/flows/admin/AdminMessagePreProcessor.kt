package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.exception.UserInputValidationException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class AdminMessagePreProcessor(private val userService: UserService): MessagePreProcessor() {

    protected fun validateOwnerPermission(userId: Int) { // todo code duplication
        val currentUserRole = userService.findUserRoleById(userId)
        if (currentUserRole != Role.ADMIN && currentUserRole != Role.OWNER) {
            throw ServiceException.AccessViolationException(userId, currentUserRole, Role.ADMIN, Role.OWNER)
        }
    }

    protected fun resolveUserId(input: String): Int { // todo code duplication
        return runCatching { input.toInt() }.getOrElse {
            throw UserInputValidationException.InvalidUserId(input)
        }
    }

    class AdminStart(private val userService: UserService, private val flowRegistry: FlowRegistry): AdminMessagePreProcessor(userService) {
        override val stepId = FlowStep.ADMIN_START

        override fun invoke(account: Account, input: String): List<String> {
            validateOwnerPermission(account.id)
            val step = flowRegistry.getFlowByUserId(account.id).current(stepId)
            val user = userService.findById(account.id)
            return listOf(
                input.format(user?.firstLastName ?: "Невідомий"),
                step.reply.options
                    .map { "${it.value.command} ${it.value.description} "}
                    .joinToString("\n")
            )
        }
    }
}
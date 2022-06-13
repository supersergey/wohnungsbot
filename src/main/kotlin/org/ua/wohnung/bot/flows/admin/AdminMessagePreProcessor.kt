package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.processors.MessageMeta
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class AdminMessagePreProcessor(private val userService: UserService) : MessagePreProcessor() {

    protected fun validateOwnerPermission(userId: Int) { // todo code duplication
        val currentUserRole = userService.findUserRoleById(userId)
        if (currentUserRole != Role.ADMIN && currentUserRole != Role.OWNER) {
            throw ServiceException.AccessViolation(userId, currentUserRole, Role.ADMIN, Role.OWNER)
        }
    }

    class AdminStart(private val userService: UserService, private val flowRegistry: FlowRegistry) :
        AdminMessagePreProcessor(userService) {
        override val stepId = FlowStep.ADMIN_START

        override fun invoke(account: Account, input: String): List<MessageMeta> {
            validateOwnerPermission(account.id)
            val step = flowRegistry.getFlowByUserId(account.id).current(stepId)
            val user = userService.findById(account.id)
                ?: throw ServiceException.UserNotFound(account.id)
            return listOf(
                MessageMeta(
                    input.format(user.firstLastName),
                    step.reply.options
                        .map { "${it.value.command} ${it.value.description} " }
                        .joinToString("\n")
                )
            )
        }
    }
}

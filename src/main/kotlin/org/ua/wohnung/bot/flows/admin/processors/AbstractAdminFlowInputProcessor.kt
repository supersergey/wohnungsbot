package org.ua.wohnung.bot.flows.admin.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

abstract class AbstractAdminFlowInputProcessor(
    userService: UserService,
    messageSource: MessageSource
) :
    AbstractUserInputProcessor(userService, messageSource) {

    abstract fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput?

    override fun processGenericCommands(chatMetadata: ChatMetadata): StepOutput? {
        assertAdminUser(chatMetadata.userId)
        return when (chatMetadata.input) {
            "/start" -> processStartCommand(chatMetadata)
            "/whoisinterested" -> processWhoIsInterestedCommand()
            "/userinfo" -> processUserInfoCommand()
            "/apartmentinfo" -> processApartmentInfoCommand()
            else -> processSpecificCommands(chatMetadata)
        }
    }

    private fun assertAdminUser(userId: Long) {
        val userRole = userService.findUserRoleById(userId)
        if (userRole !in ADMIN_ROLES) {
            throw ServiceException.AccessViolation(userId, userRole, Role.ADMIN)
        }
    }

    private fun processWhoIsInterestedCommand(): StepOutput =
        StepOutput.PlainText(
            message = messageSource[FlowStep.ADMIN_WHO_IS_INTERESTED],
            nextStep = FlowStep.ADMIN_WHO_IS_INTERESTED
        )

    private fun processUserInfoCommand(): StepOutput =
        StepOutput.PlainText(
            message = messageSource[FlowStep.ADMIN_GET_USER_INFO],
            nextStep = FlowStep.ADMIN_GET_USER_INFO
        )

    private fun processApartmentInfoCommand(): StepOutput =
        StepOutput.PlainText(
            message = messageSource[FlowStep.ADMIN_GET_APARTMENT_INFO],
            nextStep = FlowStep.ADMIN_GET_APARTMENT_INFO
        )

    protected open fun processStartCommand(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.PlainText(
            message = messageSource[FlowStep.ADMIN_START]
                .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
            nextStep = FlowStep.ADMIN_START
        )
    }

    private companion object {
        private val ADMIN_ROLES = setOf(Role.OWNER, Role.ADMIN)
    }
}

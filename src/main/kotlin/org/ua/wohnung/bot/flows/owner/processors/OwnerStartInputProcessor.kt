package org.ua.wohnung.bot.flows.owner.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class OwnerStartInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val apartmentService: ApartmentService
) :
    AbstractOwnerFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.OWNER_START

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "/listadmins" -> processListAdminsCommand()
            "/addadmin" -> processAddAdminCommand()
            "/removeadmin" -> processRemoveAdminCommand()
            "/updatetable" -> processUpdateTableCommand()
            else -> null
        }
    }

    private fun processListAdminsCommand(): StepOutput {
        val users = userService.findByRole(Role.ADMIN)
        val message = if (users.isEmpty())
            "Не знайдено"
        else
            users.joinToString("\n\n") { "${it.firstAndLastName} - https://t.me/${it.userName}" }
        return StepOutput.PlainText(
            message = message,
            nextStep = FlowStep.OWNER_START
        )
    }

    private fun processUpdateTableCommand(): StepOutput {
        val count = apartmentService.update()
        return StepOutput.PlainText(
            message = messageSource[FlowStep.OWNER_APARTMENTS_LOADED].format(count),
            nextStep = FlowStep.OWNER_START
        )
    }

    private fun processAddAdminCommand(): StepOutput {
        return StepOutput.PlainText(
            message = messageSource[FlowStep.OWNER_ADD_ADMIN],
            nextStep = FlowStep.OWNER_ADD_ADMIN
        )
    }

    private fun processRemoveAdminCommand(): StepOutput {
        return StepOutput.PlainText(
            message = messageSource[FlowStep.OWNER_REMOVE_ADMIN],
            nextStep = FlowStep.OWNER_REMOVE_ADMIN
        )
    }
}

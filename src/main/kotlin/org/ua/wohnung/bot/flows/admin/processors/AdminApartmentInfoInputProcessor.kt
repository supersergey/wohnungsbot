package org.ua.wohnung.bot.flows.admin.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.stringifiers.stringify
import org.ua.wohnung.bot.user.UserService

class AdminApartmentInfoInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val apartmentService: ApartmentService
) :
    AbstractAdminFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.ADMIN_GET_APARTMENT_INFO

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        val apartment = apartmentService.findById(chatMetadata.input)
        return StepOutput.PlainText(
            message = apartment.stringify(includeAdminFields = true),
            finishSession = true
        )
    }
}

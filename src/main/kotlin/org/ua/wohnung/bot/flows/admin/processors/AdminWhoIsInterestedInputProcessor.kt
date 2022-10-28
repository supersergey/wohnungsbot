package org.ua.wohnung.bot.flows.admin.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AdminWhoIsInterestedInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val apartmentService: ApartmentService
) : AbstractAdminFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.ADMIN_WHO_IS_INTERESTED

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return null
//        apartmentService.findApplicantsByApartmentId(chatMetadata.input)
    }
}
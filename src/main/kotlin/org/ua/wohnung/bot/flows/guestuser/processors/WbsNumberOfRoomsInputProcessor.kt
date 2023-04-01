package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.WBS_DETAILS
import org.ua.wohnung.bot.flows.step.FlowStep.WBS_NUMBER_OF_ROOMS
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class WbsNumberOfRoomsInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = WBS_NUMBER_OF_ROOMS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input !in allowedAnswers) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            wbsNumberOfRooms = chatMetadata.input.toShort()
        }
        return if (userService.findById(chatMetadata.userId)?.wbsDetails == null)
            StepOutput.PlainText(
                message = messageSource[WBS_DETAILS],
                nextStep = WBS_DETAILS
            ) else when (userService.findUserRoleById(chatMetadata.userId)) {
            Role.GUEST -> StepOutput.PlainText(
                message = messageSource[FlowStep.PHONE_NUMBER],
                nextStep = FlowStep.PHONE_NUMBER
            )
            Role.USER -> StepOutput.InlineButtons(
                message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                    .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
                nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                replyOptions = listOf("Переглянути наявне житло")
            )
            else -> null
        }
    }

    private companion object {
        private val allowedAnswers = (1..6).map { "$it" }.toSet()
    }
}

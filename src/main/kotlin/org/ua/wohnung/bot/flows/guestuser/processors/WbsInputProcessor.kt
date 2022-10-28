package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.WBS
import org.ua.wohnung.bot.flows.step.FlowStep.WBS_DETAILS
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class WbsInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = WBS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when (chatMetadata.input) {
            "так" -> {
                userService.updateUserDetails(chatMetadata.userId) {
                    wbs = true
                }
                StepOutput.PlainText(
                    message = messageSource[WBS_DETAILS],
                    nextStep = WBS_DETAILS
                )
            }
            "ні" -> {
                userService.updateUserDetails(chatMetadata.userId) {
                    wbs = false
                }
                when (userService.findUserRoleById(chatMetadata.userId)) {
                    Role.USER -> {
                        return StepOutput.InlineButtons(
                            message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                                .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
                            nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                            replyOptions = listOf("Переглянути наявне житло")
                        )
                    }
                    Role.GUEST -> StepOutput.PlainText(
                        message = messageSource[FlowStep.PHONE_NUMBER],
                        nextStep = FlowStep.PHONE_NUMBER
                    )
                    else -> null
                }
            }
            else -> null
        }
    }
}
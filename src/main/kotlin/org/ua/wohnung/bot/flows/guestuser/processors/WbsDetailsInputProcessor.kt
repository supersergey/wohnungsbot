package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class WbsDetailsInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FlowStep.WBS_DETAILS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.length > 5000) {
            return StepOutput.Error("❌ Відповідь занадто велика. Щоб почати заново, натисніть /start")
        } else {
            userService.updateUserDetails(chatMetadata.userId) {
                wbsDetails = chatMetadata.input
            }
        }
        return when (userService.findUserRoleById(chatMetadata.userId)) {
            Role.GUEST -> StepOutput.PlainText(
                message = messageSource[FlowStep.PHONE_NUMBER],
                nextStep = FlowStep.PHONE_NUMBER
            )
            Role.USER -> {
                StepOutput.InlineButtons(
                    message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                        .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
                    nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                    replyOptions = listOf("Переглянути наявне житло")
                )
            }
            else -> null
        }
    }
}

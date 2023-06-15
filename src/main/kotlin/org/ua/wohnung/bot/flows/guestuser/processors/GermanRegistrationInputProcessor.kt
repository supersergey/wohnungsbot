package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

class GermanRegistrationInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.GERMAN_REGISTRATION

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        userService.createAccount(
            Account(chatMetadata.userId, chatMetadata.chatId, chatMetadata.username, Role.GUEST)
        )
        userService.updateUserDetails(chatMetadata.userId)

        return if (chatMetadata.meta == "Так") {
            return StepOutput.PlainText(
                message = messageSource[FlowStep.POST_CODE],
                nextStep = FlowStep.POST_CODE
            )
        } else {
            userService.updateUserDetails(chatMetadata.userId) {
                postCode = "N/A"
                bundesland = BundesLand.NO_REGISTRATION.germanName
            }
            stepOutputByRole(chatMetadata)
        }
    }

    private fun stepOutputByRole(chatMetadata: ChatMetadata): StepOutput? {
        return when (userService.findUserRoleById(chatMetadata.userId)) {
            Role.GUEST -> StepOutput.InlineButtons(
                message = messageSource[FlowStep.FAMILY_COUNT],
                nextStep = FlowStep.FAMILY_COUNT,
                replyOptions = (1..12).map { "$it" }
            )
            Role.USER -> return StepOutput.InlineButtons(
                message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                    .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
                nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                replyOptions = listOf("Переглянути наявне житло")
            )
            Role.ADMIN -> StepOutput.PlainText(messageSource[FlowStep.ADMIN_START])
            Role.OWNER -> StepOutput.PlainText(messageSource[FlowStep.OWNER_START])
            null -> null
        }
    }
}

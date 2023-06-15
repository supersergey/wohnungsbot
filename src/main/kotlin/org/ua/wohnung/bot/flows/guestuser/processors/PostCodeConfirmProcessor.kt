package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class PostCodeConfirmProcessor(
    userService: UserService,
    messageSource: MessageSource
) : AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.POST_CODE_CONFIRM

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return when {
            chatMetadata.meta == null -> null
            chatMetadata.meta.startsWith("Так") -> {
                userService.updateWithPostCodeData(
                    chatMetadata.userId,
                    chatMetadata.meta.substringAfterLast(';')
                )
                return stepOutputByRole(chatMetadata)
            }

            chatMetadata.meta.startsWith("Ні") -> StepOutput.InlineButtons(
                message = messageSource[FlowStep.GERMAN_REGISTRATION],
                nextStep = FlowStep.GERMAN_REGISTRATION,
                replyOptions = listOf(
                    "Так",
                    "Ні"
                ),
                replyMetaData = listOf("Так", "Ні"),
                editMessage = false
            )

            else -> null
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

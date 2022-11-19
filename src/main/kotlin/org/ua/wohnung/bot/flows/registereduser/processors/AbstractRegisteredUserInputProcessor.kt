package org.ua.wohnung.bot.flows.registereduser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.user.UserService

abstract class AbstractRegisteredUserInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractUserInputProcessor(userService, messageSource) {
    abstract fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput?

    override fun processGenericCommands(chatMetadata: ChatMetadata): StepOutput? {
        return getErrorIfRegistrationIsNotComplete(chatMetadata) ?: run {
            userService.updateAccount(chatMetadata.userId) {
                this.username = chatMetadata.username
            }
            when (chatMetadata.input) {
                "/start" -> processStartCommand(chatMetadata)
                else -> processSpecificCommands(chatMetadata)
            }
        }
    }

    private fun getErrorIfRegistrationIsNotComplete(chatMetadata: ChatMetadata): StepOutput? {
        return if (userService.isRegistrationComplete(chatMetadata.userId)) {
            null
        } else {
            StepOutput.Error(
                message = "❌ Спочатку пройдіть реєстрацію. Для цього натисніть /start, а далі кнопку *Зарєеструватись*",
                finishSession = true
            )
        }
    }

    private fun processStartCommand(chatMetadata: ChatMetadata): StepOutput {
        val userDetails = userService.findById(chatMetadata.userId)
        return if (userDetails?.wbs == false || (userDetails?.wbs == true && userDetails.isWbsNumberOfRoomsSpecified())) {
            StepOutput.InlineButtons(
                message = messageSource[FlowStep.REGISTERED_USER_CONVERSATION_START]
                    .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
                nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                replyOptions = listOf("Переглянути наявне житло")
            )
        } else if (userDetails?.wbs == true && !userDetails.isWbsNumberOfRoomsSpecified()) {
            StepOutput.InlineButtons(
                message = messageSource[FlowStep.WBS_NUMBER_OF_ROOMS],
                nextStep = FlowStep.WBS_NUMBER_OF_ROOMS,
                replyOptions = (1..6).map { "$it" }.toList()
            )
        } else
            StepOutput.InlineButtons(
                message = messageSource[FlowStep.WBS],
                nextStep = FlowStep.WBS,
                replyOptions = listOf("Так", "Ні")
            )
    }

    private fun UserDetails?.isWbsNumberOfRoomsSpecified(): Boolean = this?.wbsNumberOfRooms != null
}

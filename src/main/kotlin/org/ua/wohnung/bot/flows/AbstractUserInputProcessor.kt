package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.*
import org.ua.wohnung.bot.user.UserService

abstract class AbstractUserInputProcessor(
    protected val userService: UserService,
    protected val messageSource: MessageSource
) : (ChatMetadata) -> StepOutput {
    abstract val supportedStep: FlowStep

    abstract fun processGenericCommands(chatMetadata: ChatMetadata): StepOutput?

    override fun invoke(chatMetadata: ChatMetadata): StepOutput {
        return when (chatMetadata.input) {
            "/site" -> processSiteCommand()
            "/conditions" -> processConditionsCommand()
            "/delete_my_data" -> processDeleteMyDataCommand(chatMetadata)
            else -> processGenericCommands(chatMetadata) ?: StepOutput.Error(
                message = Message("❌ Будь-ласка, дайте корректну відповідь на запитання. Якшо бажаєте повернутись на початок, натисніть /start"),
                finishSession = false
            )
        }
    }

    private fun processSiteCommand(): StepOutput =
        StepOutput.PlainText(
            message = Message(messageSource[FORWARD_TO_WEB]),
            finishSession = true
        )

    private fun processConditionsCommand(): StepOutput =
        StepOutput.PlainText(
            message = Message(messageSource[CONVERSATION_START]),
            finishSession = true
        )

    private fun processDeleteMyDataCommand(chatMetadata: ChatMetadata): StepOutput {
        userService.delete(chatMetadata.userId)
        return StepOutput.PlainText(
            message = Message(messageSource[CONVERSATION_FINISH_REMOVAL]),
            finishSession = true
        )
    }
}
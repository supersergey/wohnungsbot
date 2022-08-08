package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepFactory
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ForeignLanguagesFactory(
    private val messageSource: MessageSource
) : StepFactory {
    override val supportedStep = FlowStep.FOREIGN_LANGUAGES

    override fun invoke(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.PlainText(
            message = Message(messageSource[supportedStep]),
            nextStep = FlowStep.READY_TO_MOVE
        )
    }
}
package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepFactory
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AllergiesStepFactory(
    private val messageSource: MessageSource
) : StepFactory {
    override val supportedStep = FlowStep.ALLERGIES

    override fun invoke(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.PlainText(
            message = Message(messageSource[supportedStep]),
            nextStep = FlowStep.REGISTERED_USER_CONVERSATION_START
        )
    }
}
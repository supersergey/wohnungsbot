package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepFactory
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep

class ConversationFinishedDeclinedStepFactory(private val messageSource: MessageSource) : StepFactory {
    override val supportedStep = FlowStep.CONVERSATION_FINISHED_DECLINED

    override fun invoke(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.Error(Message(messageSource[supportedStep]))
    }
}
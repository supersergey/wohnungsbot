package org.ua.wohnung.bot.flows.owner.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.admin.processors.AbstractAdminFlowInputProcessor
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

abstract class AbstractOwnerFlowInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractAdminFlowInputProcessor(userService, messageSource) {

    override fun processStartCommand(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.PlainText(
            message = messageSource[FlowStep.OWNER_START]
                .format(userService.capitalizeFirstLastName(chatMetadata.userId)),
            nextStep = FlowStep.OWNER_START
        )
    }
}

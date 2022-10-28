package org.ua.wohnung.bot.flows.registereduser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.REGISTERED_USER_CONVERSATION_START
import org.ua.wohnung.bot.user.UserService

class RegisteredUserInitialInputProcessor(
    userService: UserService,
    messageSource: MessageSource
) : RegisteredUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = REGISTERED_USER_CONVERSATION_START

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? = null
}

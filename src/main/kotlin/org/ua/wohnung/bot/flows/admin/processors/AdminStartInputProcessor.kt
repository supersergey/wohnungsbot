package org.ua.wohnung.bot.flows.admin.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AdminStartInputProcessor(userService: UserService, messageSource: MessageSource): AbstractAdminFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.ADMIN_START

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? = null
}
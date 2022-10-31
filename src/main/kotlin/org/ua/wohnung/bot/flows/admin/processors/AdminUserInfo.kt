package org.ua.wohnung.bot.flows.admin.processors

import org.ua.wohnung.bot.account.AccountService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.stringifiers.stringify
import org.ua.wohnung.bot.user.UserService

class AdminUserInfo(private val accountService: AccountService, userService: UserService, messageSource: MessageSource) :
    AbstractAdminFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.ADMIN_GET_USER_INFO

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        val account = accountService.findByUsername(chatMetadata.input)
        val userDetails = account?.let { userService.findById(account.id) }
        return StepOutput.PlainText(
            message = userDetails.stringify(account),
            finishSession = true
        )
    }
}

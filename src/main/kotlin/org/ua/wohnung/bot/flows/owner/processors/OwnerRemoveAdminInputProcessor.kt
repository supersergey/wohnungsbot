package org.ua.wohnung.bot.flows.owner.processors

import org.ua.wohnung.bot.account.AccountService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.user.UserService

class OwnerRemoveAdminInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val accountService: AccountService
) :
    AbstractOwnerFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.OWNER_REMOVE_ADMIN

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        return accountService.findByUsername(chatMetadata.input)?.let {
            accountService.updateUserRole(it.id, Role.USER)
            StepOutput.PlainText(
                message = messageSource[FlowStep.OWNER_REMOVE_ADMIN_DONE].format(it.username),
                nextStep = FlowStep.OWNER_START
            )
        } ?: StepOutput.Error(
            message = "❌ Користувач не знайдений: ${chatMetadata.input}"
        )
    }
}

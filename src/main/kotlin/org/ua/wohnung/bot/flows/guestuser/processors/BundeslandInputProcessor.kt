package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.BUNDESLAND_SELECTION
import org.ua.wohnung.bot.flows.step.FlowStep.DISTRICT_SELECTION
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

class BundeslandInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = BUNDESLAND_SELECTION

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        return if (chatMetadata.input.isValidBundesLand()) {
            updateUserProfile(chatMetadata)
            return StepOutput.PlainText(
                message = messageSource[DISTRICT_SELECTION],
                nextStep = DISTRICT_SELECTION,
            )
        } else {
            StepOutput.Error(
                message = "❌ Неправильно введені дані. Будь-ласка, виберіть Федеральну Землю, натиснувши кнопку. Якшо бажаєте повернутись на початок, натисніть /start",
                finishSession = false
            )
        }
    }

    private fun String.isValidBundesLand(): Boolean =
        this in BundesLand.values().map { it.germanName.lowercase() }

    private fun updateUserProfile(chatMetadata: ChatMetadata) {
        userService.createAccount(
            Account(chatMetadata.userId, chatMetadata.chatId, chatMetadata.username, Role.GUEST)
        )
        userService.updateUserDetails(chatMetadata.userId) {
            bundesland = BundesLand.values()
                .first { it.germanName.lowercase() == chatMetadata.input }
                .toString()
        }
    }
}

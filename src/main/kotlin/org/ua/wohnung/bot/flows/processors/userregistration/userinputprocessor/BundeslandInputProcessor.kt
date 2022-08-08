package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService
import org.ua.wohnung.bot.user.model.BundesLand

class BundeslandInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.BUNDESLAND_SELECTION

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        if (chatMetadata.input in BundesLand.values().map { it.germanName.lowercase() }) {
            userService.createAccount(
                Account(chatMetadata.userId, chatMetadata.chatId, chatMetadata.username, Role.USER)
            )
            userService.updateUserDetails(chatMetadata.userId) {
                bundesland = BundesLand.values()
                    .first { it.germanName.lowercase() == chatMetadata.input }
                    .toString()
            }
            return UserInputProcessingResult.Success
        } else {
            return UserInputProcessingResult.Error(
                message = "❌ Помилка, невірно введені дані. Виберіть Землю, натиснувши кнопку, не вводьте значення вручну!",
                finishSession = false
            )
        }
    }
}
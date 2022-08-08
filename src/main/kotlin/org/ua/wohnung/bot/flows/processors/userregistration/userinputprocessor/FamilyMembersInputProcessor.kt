package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class FamilyMembersInputProcessor(
    private val userService: UserService
): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.FAMILY_MEMBERS

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        if (chatMetadata.input.isBlank()) {
            return UserInputProcessingResult.Error(
                message = "❌ Помилка, невірно введені дані. Будь-ласка, опишіть свою сімʼю.",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            familyMembers = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}
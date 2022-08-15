package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class FamilyMembersInputProcessor(
    private val userService: UserService
) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.FAMILY_MEMBERS

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 4) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            familyMembers = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}

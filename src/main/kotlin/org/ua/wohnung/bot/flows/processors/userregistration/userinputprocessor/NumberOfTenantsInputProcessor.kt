package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class NumberOfTenantsInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.FAMILY_COUNT

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (chatMetadata.input !in (1..12).map { "$it" }) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            numberOfTenants = chatMetadata.input.toShort()
        }
        return UserInputProcessingResult.Success
    }
}

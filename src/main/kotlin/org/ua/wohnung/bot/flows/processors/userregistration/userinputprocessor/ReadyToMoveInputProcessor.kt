package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ReadyToMoveInputProcessor(
    private val userService: UserService
) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.READY_TO_MOVE

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (super.invoke(chatMetadata) is UserInputProcessingResult.Success) {
            return UserInputProcessingResult.Success
        }
        if (chatMetadata.input !in setOf("так", "ні")) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            readyToMove = chatMetadata.input == "так"
        }
        return UserInputProcessingResult.Success
    }
}

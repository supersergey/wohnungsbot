package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.FAMILY_MEMBERS
import org.ua.wohnung.bot.flows.step.FlowStep.FIRSTNAME_AND_LASTNAME
import org.ua.wohnung.bot.user.UserService

class FamilyMembersInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FAMILY_MEMBERS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 4) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            familyMembers = chatMetadata.input
        }
        return StepOutput.PlainText(
            message = messageSource[FIRSTNAME_AND_LASTNAME],
            nextStep = FIRSTNAME_AND_LASTNAME
        )
    }
}

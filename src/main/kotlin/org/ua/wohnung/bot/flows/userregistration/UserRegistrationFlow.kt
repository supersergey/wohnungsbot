package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.ReplyOption
import org.ua.wohnung.bot.flows.step.StepFactory
import org.ua.wohnung.bot.user.model.BundesLand
import org.ua.wohnung.bot.user.model.Role

class UserRegistrationFlow(private val stepFactory: StepFactory) : Flow() {

    override val supportedRole = Role.GUEST

    override fun initialize() {
        stepFactory.multipleButtons(
            id = FlowStep.CONVERSATION_START,
            ReplyOption("Зареєструватися", FlowStep.ACCEPT_POLICIES)
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.ACCEPT_POLICIES,
            ReplyOption("Так", FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL),
            ReplyOption("Ні", FlowStep.CONVERSATION_FINISHED_DECLINED)
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            ReplyOption("Так", FlowStep.BUNDESLAND_SELECTION),
            ReplyOption("Ні", FlowStep.CONVERSATION_FINISHED_DECLINED)
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.BUNDESLAND_SELECTION,
            *BundesLand.values().map { it.germanName }.allTo(FlowStep.DISTRICT_SELECTION)
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.DISTRICT_SELECTION,
            next = FlowStep.FAMILY_COUNT
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.FAMILY_COUNT,
            *(1..12).map { "$it" }.allTo(FlowStep.FAMILY_MEMBERS)
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.FAMILY_MEMBERS,
            next = FlowStep.FIRSTNAME_AND_LASTNAME
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.FIRSTNAME_AND_LASTNAME,
            next = FlowStep.PHONE_NUMBER
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.PHONE_NUMBER,
            next = FlowStep.PETS
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.PETS,
            *listOf("Так", "Ні").allTo(FlowStep.FOREIGN_LANGUAGES)
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.FOREIGN_LANGUAGES,
            next = FlowStep.READY_TO_MOVE
        ).addSingle()
        stepFactory.multipleButtons(
            id = FlowStep.READY_TO_MOVE,
            *listOf("Так", "Ні").allTo(FlowStep.ALLERGIES)
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.ALLERGIES,
            next = FlowStep.REGISTERED_USER_CONVERSATION_START
        ).addSingle()
        stepFactory.termination(FlowStep.CONVERSATION_FINISHED_DECLINED).addSingle()
    }
}

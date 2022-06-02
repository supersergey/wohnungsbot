package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.BundesLand
import org.ua.wohnung.bot.user.model.Role

class UserRegistrationFlow(private val stepFactory: StepFactory) : Flow() {

    override val supportedRole = Role.GUEST

    override fun initialize() {
        stepFactory.multipleReplies(
            id = FlowStep.CONVERSATION_START,
            "Зареєструватися" to FlowStep.ACCEPT_POLICIES
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.ACCEPT_POLICIES,
            "Так" to FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            "Ні" to FlowStep.CONVERSATION_FINISHED_DECLINED
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            "Так" to FlowStep.BUNDESLAND_SELECTION,
            "Ні" to FlowStep.CONVERSATION_FINISHED_DECLINED
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.BUNDESLAND_SELECTION,
            *BundesLand.values().map { it.germanName }.allTo(FlowStep.FAMILY_COUNT)
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.FAMILY_COUNT,
            *(1..8).map { "$it" }.allTo(FlowStep.FIRSTNAME_AND_LASTNAME)
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.FIRSTNAME_AND_LASTNAME,
            next = FlowStep.PHONE_NUMBER
        ).addSingle()
        stepFactory.singleReply(
            id = FlowStep.PHONE_NUMBER,
            next = FlowStep.PETS
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.PETS,
            *listOf("Так", "Ні").allTo(FlowStep.CONVERSATION_FINISHED_SUCCESS),
        ).addSingle()
        stepFactory.multipleReplies(
            id = FlowStep.CONVERSATION_FINISHED_SUCCESS,
            "Доступне мені житло" to FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            "Видаліть мої дані" to FlowStep.CONVERSATION_FINISH_REMOVAL
        ).addSingle()
        stepFactory.termination(FlowStep.CONVERSATION_FINISHED_DECLINED).addSingle()
    }
}

package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.flows.Step
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.BundesLand

interface FlowInitializer {
    fun initialize()
}

class UserRegistrationFlowInitializer(
    private val flow: Flow,
    private val factory: StepFactory
) : FlowInitializer {
    override fun initialize() {
        factory.multipleReplies(
            id = FlowStep.CONVERSATION_START,
            "Зареєструватися" to FlowStep.ACCEPT_POLICIES
        ).add()
        factory.multipleReplies(
            id = FlowStep.ACCEPT_POLICIES,
            "Так" to FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            "Ні" to FlowStep.CONVERSATION_FINISHED_DECLINED
        ).add()
        factory.multipleReplies(
            id = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            "Так" to FlowStep.BUNDESLAND_SELECTION,
            "Ні" to FlowStep.CONVERSATION_FINISHED_DECLINED
        ).add()
        factory.multipleReplies(
            id = FlowStep.BUNDESLAND_SELECTION,
            *BundesLand.values().map { it.germanName }.allTo(FlowStep.FAMILY_COUNT)
        ).add()
        factory.multipleReplies(
            id = FlowStep.FAMILY_COUNT,
            *(1..8).map { "$it" }.allTo(FlowStep.FIRSTNAME_AND_LASTNAME)
        ).add()
        factory.singleReply(
            id = FlowStep.FIRSTNAME_AND_LASTNAME,
            next = FlowStep.PHONE_NUMBER
        ).add()
        factory.singleReply(
            id = FlowStep.PHONE_NUMBER,
            next = FlowStep.PETS
        ).add()
        factory.multipleReplies(
            id = FlowStep.PETS,
            *listOf("Так", "Ні").allTo(FlowStep.CONVERSATION_FINISHED_SUCCESS),
        ).add()
        factory.termination(FlowStep.CONVERSATION_FINISHED_SUCCESS).add()
        factory.termination(FlowStep.CONVERSATION_FINISHED_DECLINED).add()
        factory.termination(FlowStep.CONVERSATION_FINISH_REMOVAL).add()
    }

    private fun List<String>.allTo(next: FlowStep) = map { it to next }.toTypedArray()

    private fun <T : Step> T.add() {
        flow.add(this)
    }
}

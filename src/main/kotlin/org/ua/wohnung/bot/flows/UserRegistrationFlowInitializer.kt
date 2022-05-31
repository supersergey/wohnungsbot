package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor
import org.ua.wohnung.bot.user.model.BundesLand

interface FlowInitializer {
    fun initialize()
}

class UserRegistrationFlowInitializer(
    private val flow: Flow,
    private val factory: StepFactory,
    private val preProcessors: List<PreProcessor> = emptyList(),
    private val postProcessors: List<PostProcessor> = emptyList()
) : FlowInitializer {
    override fun initialize() {
        factory.multipleReplies(
            id = "conversation_start",
            "Зареєструватися" to "accept_policies",
            "Видаліть мої дані з системи" to "conversation_finish_removal",
        ).add()
        factory.multipleReplies(
            id = "accept_policies",
            "Так" to "personal_data_processing_approval",
            "Ні" to "conversation_finished_declined"
        ).add()
        factory.multipleReplies(
            id = "personal_data_processing_approval",
            "Так" to "bundesland_selection",
            "Ні" to "conversation_finished_declined"
        ).add()
        factory.multipleReplies(
            id = "bundesland_selection",
            *BundesLand.values().map { it.germanName }.allTo("family_count")
        ).add()
        factory.multipleReplies(
            id = "family_count",
            *(1..8).map { "$it" }.allTo("firstname_and_lastname")
        ).add()
        factory.singleReply(
            id = "firstname_and_lastname",
            next = "phone_number"
        ).add()
        factory.singleReply(
            id = "phone_number",
            next = "pets"
        ).add()
        factory.multipleReplies(
            id = "pets",
            *listOf("Так", "Ні").allTo("conversation_finished_success"),
        ).add()
        factory.termination("conversation_finished_success").add()
        factory.termination("conversation_finished_declined").add()
//        factory.termination("conversation_finish_removal", preProcessor = preProcessors.first { it.stepId == "conversation_finish_removal" })
    }

    private fun List<String>.allTo(next: String) = map { it to next }.toTypedArray()

    private fun <T : Step> T.add() {
        flow.add(this)
    }
}

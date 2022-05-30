package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor

interface FlowInitializer {
    fun initialize()
}

class UserRegistrationFlowInitializer(
    private val factory: StepFactory,
    private val preProcessors: List<PreProcessor> = emptyList(),
    private val postProcessors: List<PostProcessor> = emptyList()
) : FlowInitializer {
    override fun initialize() {
        factory.multipleReplies(
            id = "conversation_start",
            "Зареєструватися" to "accept_policies",
            "Видаліть мої дані з системи" to "conversation_finish_removal",
        )
        factory.multipleReplies(
            id = "accept_policies",
            "Так" to "personal_data_processing_approval",
            "Ні" to "conversation_finished_declined"
        )
        factory.multipleReplies(
            id = "personal_data_processing_approval",
            "Так" to "bundesland_selection",
            "Ні" to "conversation_finished_declined"
        )
        factory.multipleReplies(
            id = "bundesland_selection",
            *listOf(
                "Berlin",
                "Bayern (Баварія)",
                "Niedersachsen (Нижня Саксонія)",
                "Baden-Württemberg",
                "Rheinland-Pfalz (Rhineland-Palatinate)",
                "Sachsen (Саксонія)",
                "Thüringen (Тюрінгія)",
                "Hessen",
                "Nordrhein-Westfalen (Північна Рейн-Вестфалія)",
                "Sachsen-Anhalt (Саксонія Анхальт)",
                "Brandenburg",
                "Mecklenburg-Vorpommern",
                "Hamburg",
                "Schleswig-Holstein",
                "Saarland",
                "Bremen",
                "Не зареєстрований"
            ).allTo("family_count")
        )
        factory.multipleReplies(
            id = "family_count",
            *(1..8).map { "$it" }.allTo("firstname_and_lastname")
        )
        factory.singleReply(
            id = "firstname_and_lastname",
            next = "phone_number"
        )
        factory.singleReply(
            id = "phone_number",
            next = "pets"
        )
        factory.multipleReplies(
            id = "pets",
            *listOf("Так", "Ні").allTo("conversation_finished_success"),
        )
        factory.termination("conversation_finished_success")
        factory.termination("conversation_finished_declined")
        factory.termination("conversation_finish_removal", preProcessor = preProcessors.first { it.stepId == "conversation_finish_removal" })
    }

    private fun List<String>.allTo(next: String) = map { it to next }.toTypedArray()
}

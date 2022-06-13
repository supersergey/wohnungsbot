package org.ua.wohnung.bot.flows.owner

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.Role

class OwnerFlow(private val stepFactory: StepFactory) : Flow() {
    override val supportedRole = Role.OWNER

    override fun initialize() {
        stepFactory.multipleReplies(
            FlowStep.OWNER_START,
            "Оновити таблицю" to FlowStep.OWNER_APARTMENTS_LOADED
        ).addSingle()
        stepFactory.termination(
            FlowStep.OWNER_APARTMENTS_LOADED
        ).addSingle()
    }
}

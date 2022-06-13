package org.ua.wohnung.bot.flows.owner

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

class OwnerStartPostProcessor(
    private val apartmentService: ApartmentService,
) : PostProcessor {
    override val stepId = FlowStep.OWNER_START

    override fun invoke(account: Account, input: String) {
        apartmentService.update()
    }
}

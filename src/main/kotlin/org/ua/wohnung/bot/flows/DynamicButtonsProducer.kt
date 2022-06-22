package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.ReplyOption
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

interface DynamicButtonsProducer : (Account, FlowStep) -> List<ReplyOption> {
    val supportedStep: FlowStep
}

class DynamicButtonsProducerImpl(
    private val apartmentService: ApartmentService,
) : DynamicButtonsProducer {
    override val supportedStep: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS

    override fun invoke(account: Account, flowStep: FlowStep): List<ReplyOption> {
        return apartmentService
            .findByUserDetails(account.id)
            .map {
                ReplyOption(
                    "Відгукнутись на житло: ${it.id}",
                    FlowStep.REGISTERED_USER_REQUEST_RECEIVED
                )
            }
    }
}

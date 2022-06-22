package org.ua.wohnung.bot.flows.registereduser

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.processors.MessageMeta
import org.ua.wohnung.bot.flows.processors.PostProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

sealed class RegisteredUserPostProcessor: PostProcessor {
    class RegisteredUserRequestReceived(
        private val apartmentService: ApartmentService,
    ) : RegisteredUserPostProcessor() {
        override val stepId: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS

        override fun invoke(account: Account, input: String): List<MessageMeta> {
            return runCatching {
                val apartmentNumber = input.split(" ").last()
                apartmentService.acceptUserApartmentRequest(account.id, apartmentNumber)
                listOf(MessageMeta("Заявку на житло $input отримано\n", apartmentNumber))
            }.onFailure {
                when (it) {
                    is ServiceException.UserApplicationRateExceeded -> listOf(MessageMeta(it.userMessage, input))
                    is ServiceException.ApartmentNotFound -> listOf(MessageMeta(it.userMessage, input))
                    else -> throw it
                }
            }.getOrThrow()
        }
    }
}
package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.user.UserService

abstract class MessagePreProcessor : PreProcessor {

    abstract override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta>

    class RegisteredUserConversationStart(private val userService: UserService) : MessagePreProcessor() {
        override val stepId = FlowStep.REGISTERED_USER_CONVERSATION_START

        override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta> {
            return listOf(
                MessageMeta(
                    input.format(userService.findById(chatMetadata.userId)?.firstLastName ?: "Невідомий")
                )
            )
        }
    }

    class RegisteredUserListApartments(private val apartmentService: ApartmentService) : MessagePreProcessor() {
        override val stepId: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS

        override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta> {
            val apartments = apartmentService.findByUserDetails(chatMetadata.userId)
            if (apartments.isEmpty())
                throw ServiceException.MatchingApartmentNotFoundException
            else
                return apartments.map {
                    MessageMeta(
                        payload = it.toPayload(),
                        id = it.id
                    )
                }
        }

        private fun Apartment.toPayload(): String =
            StringBuilder()
                .append("Житло ✅: ").append(id)
                .append("\n\n")
                .append("\uD83D\uDC49 Земля: ").append(bundesland)
                .append("\n\n")
                .append("\uD83D\uDDFA Місто: ").append(city)
                .append("\n\n")
                .append("\uD83D\uDC69\uD83D\uDC68\u200D\uD83E\uDDB1 Кількість людей: ").append("від ").append(minTenants)
                .append(" до ").append(maxTenants)
                .append("\n\n")
                .append("(Якщо більше або менше людей, ми вас не зможемо поселити)")
                .append("\n\n")
                .append("\uD83D\uDC08\uD83D\uDC15").append(if (petsAllowed == true) "Можна з тваринами" else "Без тварин")
                .append("\n\n")
                .append("\uD83C\uDFD8 ").append(description)
                .append("\n\n")
                .append("⬆️ Житло знаходиться на поверсі: ").append(etage)
                .append("\n\n")
                .append("\uD83D\uDCCD Місцезнаходження житла на карті: ").append(mapLocation)
                .append("\n\n")
                .append("⏰ Термін проживання: ").append(livingPeriod?.takeIf { it.isNotBlank() } ?: "Не зазначений")
                .append("\n\n")
                .append("\uD83D\uDCC5 Дата показу житла: ").append(showingDate?.takeIf { it.isNotBlank() } ?: "Не зазначений")
                .toString()
    }

    object Empty : MessagePreProcessor() {
        override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta> {
            return listOf(MessageMeta(input))
        }

        override val stepId: FlowStep = FlowStep.CONVERSATION_START
    }
}

data class MessageMeta(val payload: String, val id: String = "")

package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
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
            return if (apartments.isEmpty())
                listOf(
                    MessageMeta("Нажаль, наразі ми не маємо пропозицій, які вам підходять. Якщо щось з'явиться, ви отримаєте повідомлення.")
                )
            else
                apartments.map {
                    MessageMeta(
                        payload = it.toPayload(),
                        id = it.id
                    )
                }
        }

        private fun Apartment.toPayload(): String =
            StringBuilder()
                .append("Пропозиція: ").append(id)
                .append("\n")
                .append("Земля: ").append(bundesland)
                .append("\n")
                .append("Місто: ").append(city)
                .append("\n")
                .append("Кількість людей: ").append("від ").append(minTenants)
                .append(" до ").append(maxTenants)
                .append("\n")
                .append("Можна з тваринами: ").append(if (petsAllowed == true) "так" else "ні")
                .append("\n")
                .append("Опис: ").append(description)
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

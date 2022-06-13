package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService
import java.lang.StringBuilder

abstract class MessagePreProcessor : PreProcessor {

    abstract override fun invoke(account: Account, input: String): List<String>

    class RegisteredUserConversationStart(private val userService: UserService) : MessagePreProcessor() {
        override val stepId = FlowStep.REGISTERED_USER_CONVERSATION_START

        override fun invoke(account: Account, input: String): List<String> {
            return listOf(
                input.format(userService.findById(account.id)?.firstLastName ?: "Невідомий")
            )
        }
    }

    class RegisteredUserListApartments(private val apartmentService: ApartmentService) : MessagePreProcessor() {
        override val stepId: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS

        override fun invoke(account: Account, input: String): List<String> {
            val apartments = apartmentService.findByUserDetails(account.id)
            return if (apartments.isEmpty())
                listOf("Нажаль, наразі ми не маємо пропозицій, які вам підходять. Якщо щось з'явиться, ви отримаєте повідомлення.")
            else
                apartments.map {
                    StringBuilder()
                        .append("Пропозиція: ").append(it.id)
                        .append("\n")
                        .append("Земля: ").append(it.bundesland)
                        .append("\n")
                        .append("Місто: ").append(it.city)
                        .append("\n")
                        .append("Кількість людей: ").append("від ").append(it.minTenants).append(" до ")
                        .append(it.maxTenants)
                        .append("\n")
                        .append("Можна з тваринами: ").append(if (it.petsAllowed == true) "так" else "ні")
                        .append("\n")
                        .append("Опис: ").append(it.description)
                        .toString()
                }
        }
    }

    object Empty : MessagePreProcessor() {
        override fun invoke(account: Account, input: String): List<String> {
            return listOf(input)
        }

        override val stepId: FlowStep = FlowStep.CONVERSATION_START
    }
}

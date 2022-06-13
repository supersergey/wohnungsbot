package org.ua.wohnung.bot.flows.owner

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.exception.UserInputValidationException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class OwnerMessagePreProcessor : MessagePreProcessor() {
    class OwnerStart(private val userService: UserService, private val flowRegistry: FlowRegistry) :
        OwnerMessagePreProcessor() {
        override val stepId = FlowStep.OWNER_START

        override fun invoke(account: Account, input: String): List<String> {
            val user = userService.findById(account.id)
            val step = flowRegistry.getFlowByUserId(account.id).current(stepId)
            return listOf(
                input.format(user?.firstLastName ?: "Невідомий"),
                step.reply.options
                    .map { "${it.value.command} ${it.value.description} "}
                    .joinToString("\n")
            )
        }
    }

    class OwnerApartmentsUpdated(private val apartmentService: ApartmentService) : OwnerMessagePreProcessor() {
        override val stepId = FlowStep.OWNER_APARTMENTS_LOADED

        override fun invoke(account: Account, input: String): List<String> {
            apartmentService.update()
            val count = apartmentService.count()
            return listOf(
                input.format(count)
            )
        }
    }

    class OwnerListAdmins(private val userService: UserService) : OwnerMessagePreProcessor() {
        override val stepId: FlowStep = FlowStep.OWNER_LIST_ADMINS

        override fun invoke(account: Account, input: String): List<String> {
            val admins = userService.findByRole(Role.ADMIN)
            return if (admins.isEmpty()) {
                listOf("Користувачі з ролью ${Role.ADMIN} не знайдені")
            } else
                admins.map {
                    """
                    userId: ${it.userId}
                    chatId: ${it.chatId}
                    telegramName: ${it.userName}
                    userName: ${it.firstAndLastName}
                """.trimIndent()
                }
        }
    }
}

package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.MessageMeta
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.ApartmentApplication
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.user.UserService

sealed class AdminMessagePreProcessor(private val userService: UserService) : MessagePreProcessor() {

    protected fun validateOwnerPermission(userId: Int) { // todo code duplication
        val currentUserRole = userService.findUserRoleById(userId)
        if (currentUserRole != Role.ADMIN && currentUserRole != Role.OWNER) {
            throw ServiceException.AccessViolation(userId, currentUserRole, Role.ADMIN, Role.OWNER)
        }
    }

    class AdminStart(private val userService: UserService, private val flowRegistry: FlowRegistry) :
        AdminMessagePreProcessor(userService) {
        override val stepId = FlowStep.ADMIN_START

        override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta> {
            validateOwnerPermission(chatMetadata.userId)
            val step = flowRegistry.getFlowByUserId(chatMetadata.userId).current(stepId)
            val user = userService.findById(chatMetadata.userId)
                ?: throw ServiceException.UserNotFound(chatMetadata.userId)
            return listOf(
                MessageMeta(
                    input.format(user.firstLastName) +
                            step.reply.options
                                .map { "${it.value.command} ${it.value.description} " }
                                .joinToString("\n")
                )
            )
        }
    }

    class AdminWhoIsInterested(
        userService: UserService,
        private val apartmentService: ApartmentService
    ) : AdminMessagePreProcessor(userService) {
        override val stepId: FlowStep = FlowStep.ADMIN_LIST_APPLICANTS

        override fun invoke(chatMetadata: ChatMetadata, input: String): List<MessageMeta> {
            validateOwnerPermission(chatMetadata.userId)
            return (
                    apartmentService.findApplicantsByApartmentId(chatMetadata.input)
                        .stringify()
                        .takeIf { it.isNotEmpty() }
                        ?: listOf("Апліканти не знайдені")
                    )
                .map {
                    MessageMeta(payload = it)
                }
        }

        private fun List<ApartmentApplication>.stringify() = this
            .map {
                StringBuilder().append("Номер помешкання: ${it.apartmentId}").append("\n")
                    .append("Логін телеграм: ${it.account.username ?: "Прихований"}").append("\n")
                    .append("Прізвище: ${it.userDetails.firstLastName}").append("\n")
                    .append("Телефон: ${it.userDetails.phone}").append("\n")
                    .append("Кількість людей в родині: ${it.userDetails.numberOfTenants}").append("\n")
                    .append("Чи є тварини: ${if (it.userDetails.pets) "так" else "ні"}").append("\n")
                    .append("Де зареєстрований: ${it.userDetails.bundesland}").append("\n")
                    .toString()
            }
    }
}

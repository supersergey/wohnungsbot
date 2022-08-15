// package org.ua.wohnung.bot.flows.admin
//
// import org.ua.wohnung.bot.apartment.ApartmentService
// import org.ua.wohnung.bot.exception.ServiceException
// import org.ua.wohnung.bot.flows.FlowRegistry
// import org.ua.wohnung.bot.flows.dto.ChatMetadata
// import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
// import org.ua.wohnung.bot.flows.processors.MessageStepProcessor
// import org.ua.wohnung.bot.flows.step.FlowStep
// import org.ua.wohnung.bot.persistence.ApartmentApplication
// import org.ua.wohnung.bot.persistence.generated.enums.Role
// import org.ua.wohnung.bot.user.UserService
//
// sealed class AdminMessageStepProcessor(private val userService: UserService) : MessageStepProcessor() {
//
//    protected fun validateOwnerPermission(userId: Long) { // todo code duplication
//        val currentUserRole = userService.findUserRoleById(userId)
//        if (currentUserRole != Role.ADMIN && currentUserRole != Role.OWNER) {
//            throw ServiceException.AccessViolation(userId, currentUserRole, Role.ADMIN, Role.OWNER)
//        }
//    }
//
//    class AdminStart(private val userService: UserService, private val flowRegistry: FlowRegistry) :
//        AdminMessageStepProcessor(userService) {
//        override val stepId = FlowStep.ADMIN_START
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            validateOwnerPermission(chatMetadata.userId)
//            val step = flowRegistry.getFlowByUserId(chatMetadata.userId).current(stepId)
//            val user = userService.findById(chatMetadata.userId)
//                ?: throw ServiceException.UserNotFound(chatMetadata.userId)
//            return listOf(
//                Message(
//                    input.format(user.firstLastName) +
//                        step.reply.options
//                            .map { "${it.value.userInput} ${it.value.description} " }
//                            .joinToString("\n")
//                )
//            )
//        }
//    }
//
//    class AdminWhoIsInterested(
//        userService: UserService,
//        private val apartmentService: ApartmentService
//    ) : AdminMessageStepProcessor(userService) {
//        override val stepId: FlowStep = FlowStep.ADMIN_LIST_APPLICANTS
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            validateOwnerPermission(chatMetadata.userId)
//            return (
//                apartmentService.findApplicantsByApartmentId(chatMetadata.input)
//                    .stringify()
//                    .takeIf { it.isNotEmpty() }
//                    ?: listOf("Апліканти не знайдені")
//                )
//                .map {
//                    Message(payload = it)
//                }
//        }
//
//        private fun List<ApartmentApplication>.stringify() = this
//            .map {
//                StringBuilder().append("✅ Номер помешкання: ${it.apartmentId}")
//                    .append("\n\n")
//                    .append("\uD83E\uDEAC Логін телеграм: ${it.account.username?.let { username -> "https://t.me/$username" } ?: "Прихований"}")
//                    .append("\n\n")
//                    .append("\uD83E\uDEA7 Прізвище: ${it.userDetails.firstLastName}")
//                    .append("\n\n")
//                    .append("\uD83D\uDCF1 Телефон: ${it.userDetails.phone}")
//                    .append("\n\n")
//                    .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Кількість людей в родині: ${it.userDetails.numberOfTenants}")
//                    .append("\n\n")
//                    .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Склад сімʼї: ${it.userDetails.familyMembers}")
//                    .append("\n\n")
//                    .append("\uD83D\uDC36 Чи є тварини: ${if (it.userDetails.pets) "так" else "ні"}")
//                    .append("\n\n")
//                    .append("\uD83D\uDDFA Де зареєстрований: ${it.userDetails.bundesland}")
//                    .append("\n\n")
//                    .append("\uD83D\uDCCD Район: ${it.userDetails.district}")
//                    .append("\n\n")
//                    .append("\uD83D\uDE98 Чи готові до переїзду: ${if (it.userDetails.readyToMove) "так" else "ні"}")
//                    .append("\n\n")
//                    .append("\uD83C\uDFE7 Іноземні мови: ${it.userDetails.foreignLanguages}")
//                    .append("\n\n")
//                    .append("\uD83C\uDF21 Алергії: ${it.userDetails.allergies}")
//                    .append("\n\n")
//                    .toString()
//            }
//    }
// }

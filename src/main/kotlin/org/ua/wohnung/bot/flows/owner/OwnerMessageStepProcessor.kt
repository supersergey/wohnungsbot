// package org.ua.wohnung.bot.flows.owner
//
// import org.ua.wohnung.bot.apartment.ApartmentService
// import org.ua.wohnung.bot.exception.ServiceException.UserNotFound
// import org.ua.wohnung.bot.flows.FlowRegistry
// import org.ua.wohnung.bot.flows.dto.ChatMetadata
// import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
// import org.ua.wohnung.bot.flows.processors.MessageStepProcessor
// import org.ua.wohnung.bot.flows.step.FlowStep
// import org.ua.wohnung.bot.persistence.generated.enums.Role
// import org.ua.wohnung.bot.user.UserService
//
// sealed class OwnerMessageStepProcessor : MessageStepProcessor() {
//    class OwnerStart(private val userService: UserService, private val flowRegistry: FlowRegistry) :
//        OwnerMessageStepProcessor() {
//        override val stepId = FlowStep.OWNER_START
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            val user = userService.findById(chatMetadata.userId)
//                ?: throw UserNotFound(chatMetadata.userId)
//            val step = flowRegistry.getFlowByUserId(chatMetadata.userId).current(stepId)
//            return listOf(
//                Message(
//                    input.format(user.firstLastName) + "\n" +
//                        step.reply.options
//                            .map { "${it.value.userInput} ${it.value.description} " }
//                            .joinToString("\n")
//                )
//            )
//        }
//    }
//
//    class OwnerApartmentsUpdated(private val apartmentService: ApartmentService) : OwnerMessageStepProcessor() {
//        override val stepId = FlowStep.OWNER_APARTMENTS_LOADED
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            apartmentService.update()
//            val count = apartmentService.count()
//            return listOf(
//                Message(input.format(count))
//            )
//        }
//    }
//
//    class OwnerListAdmins(private val userService: UserService) : OwnerMessageStepProcessor() {
//        override val stepId: FlowStep = FlowStep.OWNER_LIST_ADMINS
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            val admins = userService.findByRole(Role.ADMIN)
//            return if (admins.isEmpty()) {
//                listOf(Message("Користувачі з ролью ${Role.ADMIN} не знайдені"))
//            } else
//                admins.map {
//                    val userName = if (it.userName.isBlank()) { "Прихований" } else "https://t.me/${it.userName}"
//                    """
//                    userId: ${it.userId}
//                    chatId: ${it.chatId}
//                    telegramName: $userName
//                    userName: ${it.firstAndLastName}
//                    """.trimIndent()
//                }.map { Message(it) }
//        }
//    }
// }

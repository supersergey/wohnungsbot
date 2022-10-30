// package org.ua.wohnung.bot.flows.registereduser
//
// import org.ua.wohnung.bot.apartment.ApartmentService
// import org.ua.wohnung.bot.exception.ServiceException
// import org.ua.wohnung.bot.flows.dto.ChatMetadata
// import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
// import org.ua.wohnung.bot.flows.processors.StepProcessor
// import org.ua.wohnung.bot.flows.step.FlowStep
//
// sealed class RegisteredUserStepProcessor : StepProcessor {
//    class RegisteredUserRequestReceived(
//        private val apartmentService: ApartmentService,
//    ) : RegisteredUserStepProcessor() {
//        override val stepId: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS
//
//        override fun invoke(chatMetadata: ChatMetadata, input: String): List<Message> {
//            return runCatching {
//                val apartmentNumber = input.split(" ").last()
//                apartmentService.acceptUserApartmentRequest(chatMetadata.userId, apartmentNumber)
//                listOf(Message("Заявку на житло $input отримано\n", apartmentNumber))
//            }.onFailure {
//                when (it) {
//                    is ServiceException.UserApplicationRateExceeded -> listOf(Message(it.userMessage, input))
//                    is ServiceException.ApartmentNotFound -> listOf(Message(it.userMessage, input))
//                    else -> throw it
//                }
//            }.getOrThrow()
//        }
//    }
// }
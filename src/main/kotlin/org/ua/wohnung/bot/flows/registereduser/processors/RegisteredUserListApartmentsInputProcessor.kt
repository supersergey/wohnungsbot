package org.ua.wohnung.bot.flows.registereduser.processors

import org.ua.wohnung.bot.apartment.ApartmentRequestResult
import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.user.UserService
import java.time.Instant

class RegisteredUserListApartmentsInputProcessor(
    private val apartmentService: ApartmentService,
    userService: UserService,
    messageSource: MessageSource
) : RegisteredUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.startsWith("відгукнутись")) {
            return processApartmentApplication(chatMetadata)
        }
        val apartments = apartmentService.findByUserDetails(chatMetadata.userId)
        if (apartments.isEmpty()) {
            return StepOutput.PlainText(
                message = messageSource[FlowStep.REGISTERED_USER_NO_APARTMENTS_AVAILABLE],
                nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
                finishSession = true
            )
        }
        val apartmentToDisplay = when (chatMetadata.input) {
            "переглянути наявне житло" -> apartments.map { it.id }.firstOrNull()
            in apartments.map { it.id } -> apartments.map { it.id }.firstOrNull { it == chatMetadata.input }
            else -> null
        }
        return apartmentToDisplay?.let {
            apartments.toStepOutput(apartmentToDisplay, chatMetadata.input)
        }
    }

    private fun processApartmentApplication(chatMetadata: ChatMetadata): StepOutput {
        val apartmentId = chatMetadata.input.split(';').last()
        return when (
            val apartmentRequestResult = apartmentService.acceptUserApartmentRequest(chatMetadata.userId, apartmentId)
        ) {
            is ApartmentRequestResult.Success -> StepOutput.PlainText(
                message = messageSource[FlowStep.REGISTERED_USER_REQUEST_RECEIVED],
                finishSession = false
            )
            is ApartmentRequestResult.Failure -> StepOutput.PlainText(
                message = apartmentRequestResult.cause,
                finishSession = true
            )
        }
    }

    private fun List<Apartment>.toStepOutput(apartmentId: String, userInput: String): StepOutput {
        val apartmentToDisplay = first { it.id == apartmentId }
        val message = messageSource[FlowStep.REGISTERED_USER_LIST_APARTMENTS]
            .format(this.count()) + "\n\n${apartmentToDisplay.stringify()}"
        return StepOutput.InlineButtons(
            message = message,
            replyOptions = this.map { it.id } + listOf("Відгукнутись"),
            nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            replyMetaData = this.map { it.id } + listOf("Відгукнутись;$apartmentId"),
            editMessage = true
        )
    }

    private fun Apartment.stringify(): String =
        StringBuilder()
            .append("Житло ✅: ").append(id)
            .append("\n\n")
            .append("\uD83D\uDC49 Земля: ").append(bundesland)
            .append("\n\n")
            .append("\uD83D\uDDFA Місто: ").append(city)
            .append("\n\n")
            .append("\uD83D\uDC69\uD83D\uDC68\u200D\uD83E\uDDB1 Кількість людей: ").append("від ")
            .append(minTenants)
            .append(" до ").append(maxTenants)
            .append("\n\n")
            .append("(Якщо більше або менше людей, ми вас не зможемо поселити)")
            .append("\n\n")
            .append("\uD83D\uDC08\uD83D\uDC15")
            .append(if (petsAllowed == true) "Можна з тваринами" else "Без тварин")
            .append("\n\n")
            .append("\uD83C\uDFD8 ").append(description)
            .append("\n\n")
            .append("⬆️ Житло знаходиться на поверсі: ").append(etage ?: UNDEFINED)
            .append("\n\n")
            .append("\uD83D\uDCCD Місцезнаходження житла на карті: ").append(mapLocation ?: UNDEFINED)
            .append("\n\n")
            .append("⏰ Термін проживання: ").append(livingPeriod?.takeIf { it.isNotBlank() } ?: UNDEFINED)
            .append("\n\n")
            .append("\uD83D\uDCC5 Дата показу житла: ")
            .append(showingDate?.takeIf { it.isNotBlank() } ?: UNDEFINED)
            .toString()

    private companion object {
        private const val UNDEFINED = "Не зазначений"
    }
}



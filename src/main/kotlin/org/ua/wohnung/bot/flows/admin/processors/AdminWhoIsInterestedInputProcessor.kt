package org.ua.wohnung.bot.flows.admin.processors

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jooq.tools.StringUtils
import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.stringifiers.stringify
import org.ua.wohnung.bot.persistence.ApartmentApplication
import org.ua.wohnung.bot.user.UserService

class AdminWhoIsInterestedInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val apartmentService: ApartmentService,
    private val objectMapper: ObjectMapper
) : AbstractAdminFlowInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.ADMIN_WHO_IS_INTERESTED

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        val page = chatMetadata.resolveCurrentPage()
        if (page.hideCommand) {
            apartmentService.findApplicantsByApartmentId(page.apartmentId, page.cursor)
                .firstOrNull()?.account?.id?.let { apartmentService.hideApplication(page.apartmentId, it) }
            page.cursor--
        }
        val totalCount = apartmentService.countApplicationByApartmentId(page.apartmentId)
        return if (totalCount == 0) {
            StepOutput.PlainText(
                message = "На помешкання ${page.apartmentId} немає заявок, натисніть /start щоб почати заново",
                finishSession = true
            )
        } else {
            updateCursor(page, totalCount)
            val application =
                apartmentService.findApplicantsByApartmentId(page.apartmentId, page.cursor)
                    .first()
            val message = createMessage(application, page, totalCount)
            val (replyOptions, replyMetaData) = createReplyOptions(page, totalCount)
            return StepOutput.InlineButtons(
                message = message,
                nextStep = FlowStep.ADMIN_WHO_IS_INTERESTED,
                replyOptions = replyOptions,
                replyMetaData = replyMetaData,
                buttonsPerLine = 2
            )
        }
    }

    private fun updateCursor(
        page: Page,
        totalCount: Int
    ) {
        if (page.cursor < 0) {
            page.cursor = 0
        } else {
            page.cursor = minOf(page.cursor, totalCount - 1)
        }
    }

    private fun ChatMetadata.resolveCurrentPage() =
        meta?.let { objectMapper.readValue(meta) } ?: Page(input, 0)

    private fun createMessage(
        application: ApartmentApplication,
        page: Page,
        totalCount: Int
    ) = StringUtils.join(
        arrayOf(
            "Заявка номер ${page.cursor + 1} з $totalCount",
            application.userDetails.stringify(application.account),
            "Натисніть /start або /whoIsInterested, щоб подивитись анкети на інше житло"
        ),
        "\n\n"
    )

    private fun createReplyOptions(page: Page, totalCount: Int): Pair<List<String>, List<String>> {
        if (totalCount == 1) {
            return listOf(HIDE_CAPTION) to listOf(hide(page))
        }
        if (page.cursor == 0) {
            return listOf("⏩", "⏭️", HIDE_CAPTION) to
                listOf(next(page), last(page, totalCount), hide(page))
        }
        if (page.cursor == totalCount - 1) {
            return listOf("⏮️", "⏪", HIDE_CAPTION) to
                listOf(first(page), prev(page), hide(page))
        }
        return listOf("⏮️", "⏪", "⏩", "⏭️", HIDE_CAPTION) to
            listOf(first(page), prev(page), next(page), last(page, totalCount), hide(page))
    }

    private fun first(page: Page): String = objectMapper.writeValueAsString(Page(page.apartmentId, 0))
    private fun prev(page: Page): String = objectMapper.writeValueAsString(Page(page.apartmentId, page.cursor - 1))
    private fun next(page: Page): String = objectMapper.writeValueAsString(Page(page.apartmentId, page.cursor + 1))
    private fun last(page: Page, totalCount: Int): String =
        objectMapper.writeValueAsString(Page(page.apartmentId, totalCount - 1))

    private fun hide(page: Page): String =
        objectMapper.writeValueAsString(Page(page.apartmentId, page.cursor, hideCommand = true))

    data class Page(
        val apartmentId: String,
        var cursor: Int,
        val hideCommand: Boolean = false
    )

    private companion object {
        const val HIDE_CAPTION = "Сховати"
    }
}

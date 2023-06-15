package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.persistence.PostCodeRepository
import org.ua.wohnung.bot.user.UserService

class PostCodeInputProcessor(
    userService: UserService,
    messageSource: MessageSource,
    private val postCodeRepository: PostCodeRepository,
) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep = FlowStep.POST_CODE

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        return chatMetadata.input.matchesPostCodePattern()?.let {
            postCodeRepository.findByPostCode(it)?.let { postCode ->
                StepOutput.InlineButtons(
                    message = """
                    Ваша реєстрація: земля ${postCode.landName}, місто/район ${postCode.plzName}. Правильно?    
                    """.trimIndent(),
                    nextStep = FlowStep.POST_CODE_CONFIRM,
                    replyOptions = listOf("Так", "Ні"),
                    replyMetaData = listOf("Так;${postCode.id}", "Ні")
                )
            }
        } ?: StepOutput.Error("❌ Неправильно введені дані. Вкажіть німецький поштовий індекс місця вашої реєстрації. Щоб припинити реєстрацію і повернутись на початок, натисніть /start", finishSession = false)
    }

    private fun String.matchesPostCodePattern(pattern: Regex = GERMAN_REGEXP_PATTERN): String? =
        if (this.trim().matches(pattern)) {
            this.trim()
        } else {
            null
        }

    private companion object {
        val GERMAN_REGEXP_PATTERN = "\\d{4,5}".toRegex()
    }
}

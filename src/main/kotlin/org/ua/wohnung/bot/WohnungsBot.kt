package org.ua.wohnung.bot

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.Reply

class WohnungsBot(
    private val secret: String,
    private val userRegistrationFlow: Flow,
    private val session: Session,
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
        logger.info { "Received update, chatId: ${update.message.chatId}" }
        kotlin.runCatching {
            if (update.hasMessage() && update.message.hasText() && update.message.isUserMessage) {
                val chatId = update.message.chatId
                val currentStepId = session.current(chatId) ?: userRegistrationFlow.first().caption

                val nextMessage = userRegistrationFlow.next(
                    stepId = currentStepId, userInput = update.message.text
                ) ?: userRegistrationFlow.first()

                SendMessage().apply {
                    this.chatId = chatId.toString()
                    text = nextMessage.caption
                    if (nextMessage.reply is Reply.Inline) {
                        replyMarkup = ReplyKeyboardMarkup().apply {
                            oneTimeKeyboard = true
                            keyboard = (nextMessage.reply as Reply.Inline).keyboardRows(3)
                        }
                    } else {
                        replyMarkup = null
                    }
                    session.updateState(chatId.toLong(), nextMessage.id)
                }.let {
                    execute(it)
                }
            }
        }.onFailure {
            println(it.stackTraceToString())
            logger.error { it }
        }
    }

    private fun Reply.Inline.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return options
            .map { it.key }
            .map { KeyboardButton(it) }
            .chunked(buttonsPerRow)
            .map { KeyboardRow(it) }
    }

//    companion object {
//        const val BOT_NAME = "UA_Wohnung_Bot"
//        const val CREATOR_ID = 193689183L
//    }
}

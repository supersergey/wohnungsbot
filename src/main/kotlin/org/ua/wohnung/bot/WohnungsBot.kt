package org.ua.wohnung.bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.security.SecretContainer

class WohnungsBot(
    private val secretContainer: SecretContainer,
    private val userRegistrationFlow: UserRegistrationFlow,
    private val session: Session
) : TelegramLongPollingBot() {
    override fun getBotToken(): String = secretContainer.getApiToken()

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
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
                            this.keyboard = listOf(
                                KeyboardRow().apply {
                                    this.addAll(nextMessage.reply.options.map { it.key })
                                }
                            )
                        }
                    }
                }.let {
                    execute(it)
                    session.updateState(it.chatId.toLong(), it.text)
                }
            }
        }.onFailure {
            println(it.message)
            println(it.stackTraceToString())
        }
    }

//    companion object {
//        const val BOT_NAME = "UA_Wohnung_Bot"
//        const val CREATOR_ID = 193689183L
//    }
}


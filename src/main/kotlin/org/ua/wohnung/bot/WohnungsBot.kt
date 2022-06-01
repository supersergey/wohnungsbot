package org.ua.wohnung.bot

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.Reply
import org.ua.wohnung.bot.flows.dto.UserInput
import org.ua.wohnung.bot.flows.userregistration.Flow
import org.ua.wohnung.bot.user.UserService

class WohnungsBot(
    private val secret: String,
    private val userRegistrationFlow: Flow,
    private val session: Session,
    private val userService: UserService
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
        logger.info { "Received update, chatId: ${update.message.chatId}" }
        if (update.isProcessable()) {
            kotlin.runCatching {
                val incomingMessage = update.input()
                val currentStep = session.current(incomingMessage.chatId)?.let { stepId ->
                    userRegistrationFlow.current(stepId)
                } ?: userRegistrationFlow.first()

                currentStep.postProcessor(incomingMessage.username, incomingMessage.input)

                val nextStep = userRegistrationFlow.next(
                    currentStep = currentStep.id, userInput = incomingMessage.input
                ) ?: userRegistrationFlow.first()

                nextStep.preProcessor(incomingMessage.username, incomingMessage.input)

                val sendMessage = SendMessage().apply {
                    chatId = incomingMessage.chatId.toString()
                    text = nextStep.caption
                    if (nextStep.reply is Reply.Inline) {
                        replyMarkup = ReplyKeyboardMarkup().apply {
                            oneTimeKeyboard = true
                            keyboard = (nextStep.reply as Reply.Inline).keyboardRows(3)
                        }
                    } else {
                        replyMarkup = null
                    }
                    session.updateState(chatId.toLong(), nextStep.id)
                }
                execute(sendMessage)
                nextStep.preProcessor(incomingMessage.username, incomingMessage.input)
            }.onFailure {
                println(it.stackTraceToString())
                logger.error { it }
            }
        }
    }

    private fun Update.isProcessable(): Boolean =
        hasMessage() && message.hasText() && message.isUserMessage

    private fun Update.input(): UserInput =
        UserInput(
            username = message.chat.userName,
            chatId = message.chatId,
            input = message.text
        )

    private fun Reply.Inline.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return options
            .map { it.key }
            .map { KeyboardButton(it) }
            .chunked(buttonsPerRow)
            .map { KeyboardRow(it) }
    }
}

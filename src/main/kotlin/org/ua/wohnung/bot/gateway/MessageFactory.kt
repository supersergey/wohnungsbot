package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.Reply
import org.ua.wohnung.bot.flows.Step
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.flows.processors.PreProcessor
import org.ua.wohnung.bot.flows.processors.ProcessorContainer
import org.ua.wohnung.bot.flows.processors.ProcessorContainer.*

class MessageFactory() {
    fun newStepMessage(chatIdentifier: Long, step: Step): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = step.caption
        if (step.reply is Reply.Inline) {
            replyMarkup = ReplyKeyboardMarkup().apply {
                oneTimeKeyboard = true
                keyboard = (step.reply as Reply.Inline).keyboardRows(3)
            }
        } else {
            replyMarkup = null
        }
    }

    fun newCustomMessage(chatIdentifier: Long, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun Reply.Inline.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return options
            .map { it.key }
            .map { KeyboardButton(it) }
            .chunked(buttonsPerRow)
            .map { KeyboardRow(it) }
    }
}
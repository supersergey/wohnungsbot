package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.Reply
import org.ua.wohnung.bot.flows.Step

class MessageFactory {
    fun newStepMessage(chatIdentifier: Int, step: Step): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = step.caption
        if (step.reply is Reply.WithButtons) {
            replyMarkup = ReplyKeyboardMarkup().apply {
                oneTimeKeyboard = true
                keyboard = (step.reply as Reply.WithButtons).keyboardRows(3)
            }
        } else {
            replyMarkup = null
        }
    }

    fun newCustomMessage(chatIdentifier: Int, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun Reply.WithButtons.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return options
            .map { it.key }
            .map { KeyboardButton(it) }
            .chunked(buttonsPerRow)
            .map { KeyboardRow(it) }
    }
}

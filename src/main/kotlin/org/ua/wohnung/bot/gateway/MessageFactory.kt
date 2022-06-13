package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.ua.wohnung.bot.flows.Reply
import org.ua.wohnung.bot.flows.Step

class MessageFactory {
    fun newStepMessage(chatIdentifier: Int, step: Step): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = step.caption
        if (step.reply is Reply.WithButtons) {
            replyMarkup = InlineKeyboardMarkup().apply {
//                oneTimeKeyboard = true
                keyboard = (step.reply as Reply.WithButtons).keyboardRows(3)
            }
        }
    }

    fun newCustomMessage(chatIdentifier: Int, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun Reply.WithButtons.keyboardRows(buttonsPerRow: Int): List<List<InlineKeyboardButton>> {
        return options
            .map { it.key }
            .map {
                InlineKeyboardButton.builder()
                    .callbackData("abc")
                    .text(it)
                    .build()
            }
            .chunked(buttonsPerRow)

    }
}

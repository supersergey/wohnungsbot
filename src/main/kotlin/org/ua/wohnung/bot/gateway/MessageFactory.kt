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
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.MessageMeta
import org.ua.wohnung.bot.flows.processors.ProcessorContainer
import org.ua.wohnung.bot.flows.processors.ProcessorContainer.*
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

class MessageFactory(private val messagePreProcessors: MessagePreProcessors) {
    fun get(account: Account, step: Step): List<SendMessage> {
        val messages: List<MessageMeta> = messagePreProcessors[step.id].invoke(account, step.caption)
        return messages.map { messageMeta ->
            SendMessage.builder()
                .chatId(account.chatId.toString())
                .text(messageMeta.payload)
                .withMarkup(step, messageMeta)
                .build()
        }
    }

    fun getCustom(chatIdentifier: Int, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun SendMessage.SendMessageBuilder.withMarkup(
        step: Step,
        messageMeta: MessageMeta
    ): SendMessage.SendMessageBuilder {
        this.replyMarkup(
            when (step.reply) {
                is Reply.WithButtons -> {
                    ReplyKeyboardMarkup.builder()
                        .oneTimeKeyboard(true)
                        .keyboard(
                            (step.reply as Reply.WithButtons).keyboardRows(3)
                        ).build()
                }
                is Reply.WithInlineButtons -> {
                    InlineKeyboardMarkup.builder()
                        .keyboard(
                            (step.reply as Reply.WithInlineButtons).inlineKeyboardRows(messageMeta, 3)
                        ).build()
                }
                else -> ReplyKeyboardMarkup.builder().clearKeyboard().build()
            }
        )
        return this
    }

    private fun Reply.WithButtons.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return options
            .map { it.key }
            .map { KeyboardButton(it) }
            .chunked(buttonsPerRow)
            .map { KeyboardRow(it) }
    }

    private fun Reply.WithInlineButtons.inlineKeyboardRows(
        meta: MessageMeta,
        buttonsPerRow: Int
    ): List<List<InlineKeyboardButton>> {
        return this.options
            .map { InlineKeyboardButton.builder()
                .text(it.key.format(meta.id))
                .callbackData(meta.id)
                .build()
            }
            .chunked(buttonsPerRow)
    }
}

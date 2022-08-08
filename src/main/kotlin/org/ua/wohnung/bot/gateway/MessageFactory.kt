package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput

data class MessageWrapper(
    val editMessage: EditMessageText? = null,
    val sendMessage: SendMessage? = null,
    val messageId: Int? = null
)

class MessageFactory {
    fun get(chatMeta: ChatMetadata, stepOutput: StepOutput): MessageWrapper {
        return if (stepOutput is StepOutput.InlineButtons && stepOutput.isEditMessage) {
            MessageWrapper(
                editMessage =
                EditMessageText.builder()
                    .chatId(chatMeta.chatId)
                    .messageId(chatMeta.messageId)
                    .text(stepOutput.message.payload)
                    .replyMarkup(InlineKeyboardMarkup.builder().withKeyboard(stepOutput).build())
                    .build()
            )
        } else {
            MessageWrapper(
                sendMessage = SendMessage.builder()
                    .chatId(chatMeta.chatId.toString())
                    .text(stepOutput.message.payload)
                    .withMarkup(stepOutput)
                    .build(),
                messageId = chatMeta.messageId
            )
        }

    }

    fun getCustom(chatIdentifier: Long?, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun SendMessage.SendMessageBuilder.withMarkup(
        stepOutput: StepOutput
    ): SendMessage.SendMessageBuilder {
        return this.replyMarkup(
            when (stepOutput) {
                is StepOutput.PlainText, is StepOutput.Error -> {
                    ReplyKeyboardRemove(true)
                }
                is StepOutput.MarkupButtons -> {
                    ReplyKeyboardMarkup.builder().withKeyboard(stepOutput).build()
                }
                is StepOutput.InlineButtons -> {
                    InlineKeyboardMarkup.builder().withKeyboard(stepOutput).build()
                }
            }
        )
    }

    private fun InlineKeyboardMarkup.InlineKeyboardMarkupBuilder.withKeyboard(
        stepOutput: StepOutput.InlineButtons,
        buttonsPerRow: Int = 3
    ): InlineKeyboardMarkup.InlineKeyboardMarkupBuilder =
        keyboard(
            stepOutput.replyOptions
                .map { InlineKeyboardButton(it, null, it, null, null, null, null, null, null) }
                .chunked(buttonsPerRow))

    private fun ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder.withKeyboard(
        stepOutput: StepOutput.MarkupButtons,
        buttonsPerRow: Int = 3
    ): ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder =
        keyboard(
            stepOutput.replyOptions
                .map { KeyboardButton(it) }
                .chunked(buttonsPerRow)
                .map { KeyboardRow(it) }
        )
            .oneTimeKeyboard(true)
}

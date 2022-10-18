package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText.EditMessageTextBuilder
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import java.io.Serializable

class MessageFactory {
    @Suppress("UNCHECKED_CAST")
    fun get(chatMeta: ChatMetadata, stepOutput: StepOutput): BotApiMethod<Serializable> {
        return if (stepOutput.editMessage) {
            EditMessageText.builder()
                .chatId(chatMeta.chatId)
                .messageId(chatMeta.messageId)
                .text(stepOutput.message)
                .withMarkup(stepOutput)
                .build()

        } else
            SendMessage.builder()
                .chatId(chatMeta.chatId.toString())
                .text(stepOutput.message)
                .withMarkup(stepOutput)
                .build() as BotApiMethod<Serializable>
    }


    fun getCustom(chatIdentifier: Long?, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun EditMessageTextBuilder.withMarkup(stepOutput: StepOutput): EditMessageTextBuilder {
        return this.replyMarkup(buildReplyMarkup(stepOutput) as InlineKeyboardMarkup)
    }

    private fun SendMessage.SendMessageBuilder.withMarkup(stepOutput: StepOutput): SendMessage.SendMessageBuilder {
        return this.replyMarkup(buildReplyMarkup(stepOutput))
    }

    private fun buildReplyMarkup(stepOutput: StepOutput): ReplyKeyboard {
        return when (stepOutput) {
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
    }


    private fun InlineKeyboardMarkup.InlineKeyboardMarkupBuilder.withKeyboard(
        stepOutput: StepOutput.InlineButtons,
        buttonsPerRow: Int = 3
    ): InlineKeyboardMarkup.InlineKeyboardMarkupBuilder =
        keyboard(
            stepOutput.replyOptions
                .mapIndexed { i, buttonCaption ->
                    InlineKeyboardButton.builder()
                        .callbackData(stepOutput.replyMetaData?.get(i) ?: buttonCaption)
                        .text(buttonCaption)
                        .build()
                }
                .chunked(buttonsPerRow)
        )

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


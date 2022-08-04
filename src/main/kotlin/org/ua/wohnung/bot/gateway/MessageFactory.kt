package org.ua.wohnung.bot.gateway

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.dynamicbuttons.DynamicButtonProducersRegistry
import org.ua.wohnung.bot.flows.processors.MessageMeta
import org.ua.wohnung.bot.flows.processors.ProcessorContainer.MessagePreProcessors
import org.ua.wohnung.bot.flows.step.Reply
import org.ua.wohnung.bot.flows.step.ReplyOption
import org.ua.wohnung.bot.flows.step.Step

class MessageFactory(
    private val messagePreProcessors: MessagePreProcessors,
    private val dynamicButtonsProducersRegistry: DynamicButtonProducersRegistry
) {
    fun get(chatMeta: ChatMetadata, step: Step): List<SendMessage> {
        val messages: List<MessageMeta> = messagePreProcessors[step.id].invoke(chatMeta, step.caption)
        return messages.map { messageMeta ->
            SendMessage.builder()
                .chatId(chatMeta.chatId.toString())
                .text(messageMeta.payload)
                .withMarkup(chatMeta, step, messageMeta)
        }
    }

    fun getCustom(chatIdentifier: Long?, message: String): SendMessage = SendMessage().apply {
        this.chatId = chatIdentifier.toString()
        text = message
    }

    private fun SendMessage.SendMessageBuilder.withMarkup(
        chatMeta: ChatMetadata,
        step: Step,
        messageMeta: MessageMeta
    ): SendMessage {
        this.replyMarkup(
            when (step.reply) {
                is Reply.WithButtons -> {
                    ReplyKeyboardMarkup.builder()
                        .oneTimeKeyboard(true)
                        .keyboard(
                            (step.reply as Reply.WithButtons).options.keyboardRows(3)
                        ).build()
                }
                is Reply.WithDynamicButtons -> {
                    dynamicButtonsProducersRegistry[step.id]?.let { buttonsProducer ->
                        buttonsProducer(chatMeta, (step.reply as Reply.WithDynamicButtons).nextStep)
                            .associateBy { it.command }
                            .keyboardRows(3)
                    }.takeIf { !it.isNullOrEmpty() }?.let {
                        ReplyKeyboardMarkup.builder()
                            .oneTimeKeyboard(true)
                            .keyboard(it)
                            .build()
                    }
                }
                is Reply.WithInlineButtons -> {
                    InlineKeyboardMarkup.builder()
                        .keyboard(
                            (step.reply as Reply.WithInlineButtons).inlineKeyboardRows(messageMeta, 3)
                        ).build()
                }
                else -> ReplyKeyboardMarkup.builder()
                    .keyboard(emptyList())
                    .clearKeyboard()
                    .build()
            }
        )
        return this.build()
    }

    private fun Map<String, ReplyOption>.keyboardRows(buttonsPerRow: Int): List<KeyboardRow> {
        return this
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
            .map {
                InlineKeyboardButton.builder()
                    .text(it.key.format(meta.id))
                    .callbackData(meta.id)
                    .build()
            }
            .chunked(buttonsPerRow)
    }
}

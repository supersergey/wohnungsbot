package org.ua.wohnung.bot.flows.dto

data class ChatMetadata(
    val userId: Long,
    val chatId: Long,
    val username: String?,
    val input: String
)

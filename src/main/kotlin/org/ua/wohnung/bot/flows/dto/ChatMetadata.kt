package org.ua.wohnung.bot.flows.dto

data class ChatMetadata(
    val userId: Int,
    val chatId: Int,
    val username: String?,
    val input: String
)

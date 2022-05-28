package com.example.testapp.components.message.list.model

import com.example.testapp.components.message.list.adapter.MessageListItem

/**
 * MessageListItemWrapper wraps a list of [MessageListItem] with a few extra fields.
 */
public data class MessageListItemWrapper(
    val items: List<MessageListItem> = listOf(),
    val hasNewMessages: Boolean = false,
    val isTyping: Boolean = false,
//    val isThread: Boolean = false
)

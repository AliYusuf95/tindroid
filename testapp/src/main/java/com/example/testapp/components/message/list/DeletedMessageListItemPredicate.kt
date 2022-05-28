package com.example.testapp.components.message.list

import com.example.testapp.client.ChatClient
import com.example.testapp.components.message.list.adapter.MessageListItem

/**
 * Predicate class used to filter [MessageListItem.MessageItem] items which are deleted. Used by [MessageListView.setDeletedMessageListItemPredicate].
 */
public sealed class DeletedMessageListItemPredicate : MessageListView.MessageListItemPredicate {
    /**
     * Predicate object used to hide deleted [MessageListItem.MessageItem] items from everyone.
     */
    public object NotVisibleToAnyone : DeletedMessageListItemPredicate() {
        override fun predicate(item: MessageListItem): Boolean {
            return false
        }
    }

    /**
     * Predicate object used to show deleted [MessageListItem.MessageItem] items to everyone.
     */
    public object VisibleToEveryone : DeletedMessageListItemPredicate() {
        override fun predicate(item: MessageListItem): Boolean {
            return true
        }
    }

    /**
     * Predicate object used to hide deleted [MessageListItem.MessageItem] items from everyone except for the author of the message.
     */
    public object VisibleToAuthorOnly : DeletedMessageListItemPredicate() {
        override fun predicate(item: MessageListItem): Boolean {
            return ChatClient.instance().getCurrentUser()?.let { user ->
                item is MessageListItem.MessageItem && item.message.user.id == user.id
            } ?: false
        }
    }
}

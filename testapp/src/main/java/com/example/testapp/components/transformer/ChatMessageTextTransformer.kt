package com.example.testapp.components.transformer

import android.widget.TextView
import com.example.testapp.components.message.list.adapter.MessageListItem


/**
 * Transforms a [MessageListItem.MessageItem] to format or style the [TextView].
 *
 * Instances can be provided by implementing this interface and [installing][ChatUI.messageTextTransformer]
 * that in [ChatUI].
 */
public fun interface ChatMessageTextTransformer {

    /**
     * Transforms a given [MessageListItem.MessageItem] and sets the formatted string to the [TextView].
     *
     * For example, this implementation would convert the message to upper case
     * and then set it to the textView.
     * ```
     * val toUpperCaseTransformer = ChatMessageTextTransformer { textView, messageItem ->
     *      val upperCaseMessage = messageItem.message.text.uppercase(Locale.getDefault())
     *      textView.text = upperCaseMessage
     * }
     */
    public fun transformAndApply(textView: TextView, messageItem: MessageListItem.MessageItem)
}

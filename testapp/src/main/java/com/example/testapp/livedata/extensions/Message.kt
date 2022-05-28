package com.example.testapp.livedata.extensions

import com.example.testapp.client.models.Channel
import com.example.testapp.client.models.Message
import com.example.testapp.client.models.User
import java.util.*

/** Updates collection of messages with more recent data of [users]. */
internal fun Collection<Message>.updateUsers(users: Map<String, User>) =
    map { it.updateUsers(users) }

/**
 * Updates a message with more recent data of [users]. It updates author user, latestReactions, replyTo message,
 * mentionedUsers, threadParticipants and pinnedBy user of this instance.
 */
internal fun Message.updateUsers(users: Map<String, User>): Message =
    if (users().map(User::id).any(users::containsKey)) {
        copy(
            user = if (users.containsKey(user.id)) {
                users[user.id] ?: user
            } else user,
//            latestReactions = latestReactions.updateByUsers(users).toMutableList(),
            replyTo = replyTo?.updateUsers(users),
            mentionedUsers = mentionedUsers.updateUsers(users).toMutableList(),
//            threadParticipants = threadParticipants.updateUsers(users).toMutableList(),
//            pinnedBy = users[pinnedBy?.id ?: ""] ?: pinnedBy,
        )
    } else {
        this
    }

/**
 * Fills [Message.mentionedUsersIds] based on [Message.text] and [Channel.members].
 *
 * It combines the users found in the input with pre-set [Message.mentionedUsersIds], in case someone
 * is manually added as a mention. Currently only searches through the channel members for possible mentions.
 *
 * @param channel The channel whose members we can check for the mention.
 */
internal fun Message.populateMentions(channel: Channel) {
    if ('@' !in text) {
        return
    }

    val text = text.lowercase()
    val mentions =
        mentionedUsersIds.toMutableSet() + channel.members.mapNotNullTo(mutableListOf()) { member ->
            if (text.contains("@${member.user.name.lowercase()}")) {
                member.user.id
            } else {
                null
            }
        }

    mentionedUsersIds = mentions.toMutableList()
}

internal val NEVER = Date(0)

internal fun Message.wasCreatedAfterOrAt(date: Date?): Boolean {
    return (createdAt ?: createdLocallyAt ?: NEVER) >= date
}

internal fun Message.wasCreatedAfter(date: Date?): Boolean {
    return (createdAt ?: createdLocallyAt ?: NEVER) > date
}

internal fun Message.wasCreatedBefore(date: Date?): Boolean {
    return (createdAt ?: createdLocallyAt ?: NEVER) < date
}

internal fun Message.wasCreatedBeforeOrAt(date: Date?): Boolean {
    return (createdAt ?: createdLocallyAt ?: NEVER) <= date
}

internal fun Message.users(): List<User> {
//    return latestReactions.mapNotNull(Reaction::user) +
    return listOf(user) +
            (replyTo?.users().orEmpty()) +
            mentionedUsers
//            ownReactions.mapNotNull(Reaction::user) +
//            threadParticipants +
//            (pinnedBy?.let { listOf(it) } ?: emptyList())
}
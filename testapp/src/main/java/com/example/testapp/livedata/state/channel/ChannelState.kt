package com.example.testapp.livedata.state.channel

import com.example.testapp.client.models.*
import com.example.testapp.core.internal.InternalTinUiApi
import com.example.testapp.livedata.model.ChannelData
import kotlinx.coroutines.flow.StateFlow

@InternalTinUiApi
/** State container with reactive data of a channel.*/
public interface ChannelState {
    /** Id of this channel.*/
    public val channelId: String

    /** CID of this channel. It's 'channelType:channelId'.*/
//    public val cid: String

    /** A replied message state in this channel. By default is null. There is a value if you're replying some message.*/
    public val repliedMessage: StateFlow<Message?>

    /** The message collection of this channel. */
    public val messages: StateFlow<List<Message>>

    /** Strong typed state of message collection of this channel. See [MessagesState] for more details.*/
    public val messagesState: StateFlow<MessagesState>

    /** The collection of messages from previous pages of data.*/
    public val oldMessages: StateFlow<List<Message>>

    /** The number of people currently watching the channel.*/
    public val watcherCount: StateFlow<Int>

    /** The list of users currently watching this channel.*/
    public val watchers: StateFlow<List<User>>

    /** Who is currently typing. Current user is excluded from this. */
    public val typing: StateFlow<TypingEvent>

    /** How far every user in this channel has read. */
    public val reads: StateFlow<List<ChannelUserRead>>

    /** Read status for the current user. */
    public val read: StateFlow<ChannelUserRead?>

    /** Unread count for this channel, calculated based on read state. This works even if you're offline. */
    public val unreadCount: StateFlow<Int?>

    /** The list of members of this channel. */
    public val members: StateFlow<List<Member>>

    /** StateFlow object with the channel data. */
    public val channelData: StateFlow<ChannelData>

    /** If the channel is currently hidden. */
    public val hidden: StateFlow<Boolean>

    /** If the channel is currently muted. */
    public val muted: StateFlow<Boolean>

    /** If we are currently loading. */
    public val loading: StateFlow<Boolean>

    /** If we are currently loading older messages. */
    public val loadingOlderMessages: StateFlow<Boolean>

    /** If we are currently loading newer messages. */
    public val loadingNewerMessages: StateFlow<Boolean>

    /** If there are no more older messages to load. */
    public val endOfOlderMessages: StateFlow<Boolean>

    /** If there are no more newer messages to load. */
    public val endOfNewerMessages: StateFlow<Boolean>

    /** If we need to recover state when connection established again. */
    public val recoveryNeeded: Boolean

    /** Function that builds a channel based on data from StateFlows. */
    public fun toChannel(): Channel
}

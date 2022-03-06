package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.channels.MessageChannel
import java.time.OffsetDateTime

data class MessageData(
    override val client: KodiClient,
    override val id: Long
) : Message {
    override lateinit var content: String
    override var channelId: Long = 0L
    override var authorId: Long = 0L
    override var guildId: Long = 0L
    override lateinit var timestamp: OffsetDateTime

    override suspend fun channel() =
        client.tempChannelCache[channelId] as? MessageChannel

    override suspend fun author() =
        client.tempUserCache[authorId]

    override suspend fun member() =
        client.tempMemberCache[authorId]

    override suspend fun guild() =
        client.tempGuildCache[guildId]
}

interface Message : ISnowflake {
    override val client: KodiClient
    override val id: Long
    val content: String
    val channelId: Long
    val authorId: Long
    val guildId: Long?
    val timestamp: OffsetDateTime

    suspend fun channel(): MessageChannel?
    suspend fun author(): User?
    suspend fun member(): Member?
    suspend fun guild(): Guild?
}

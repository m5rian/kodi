package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.gateway.cache.MemberCacheKey
import net.pringles.kodi.models.channels.MessageChannel
import java.time.OffsetDateTime

data class MessageData(
    override val client: KodiClient,
    override val id: Long
) : Message {
    override lateinit var content: String
    override var channelId: Long = 0L
    override var authorId: Long = 0L
    override var guildId: Long? = null
    override lateinit var timestamp: OffsetDateTime

    override suspend fun channel() = client.channelCache.get(channelId) as? MessageChannel

    override suspend fun author() = client.userCache.get(authorId)

    override suspend fun member() = guildId?.let { client.memberCache.get(MemberCacheKey(it, authorId)) }

    override suspend fun guild() = guildId?.let { client.guildCache.get(it) }
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

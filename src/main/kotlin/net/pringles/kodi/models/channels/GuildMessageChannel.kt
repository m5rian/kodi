package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient

open class GuildMessageChannelData(
    client: KodiClient,
    guildId: Long,
    id: Long,
) : GuildChannelData(client, guildId, id), GuildMessageChannel {
    override var parentId: Long = 0
    override var rateLimitPerUser: Int = 0
    override var nsfw: Boolean = false
    override var topic: String? = null
    override var lastMessageId: Long? = null
}

interface GuildMessageChannel : GuildChannel, MessageChannel, ChildrenChannel {
    override val client: KodiClient
    override val id: Long
    override val type: ChannelType
    override val guildId: Long
    override val name: String
    override val position: Int
    override val permissionOverwrites: List<*>
    override val parentId: Long
    val rateLimitPerUser: Int
    val nsfw: Boolean
    val topic: String?
    val lastMessageId: Long?
}

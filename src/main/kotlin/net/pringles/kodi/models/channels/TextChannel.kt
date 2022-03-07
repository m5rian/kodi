package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient

class TextChannelData(
    client: KodiClient,
    guildId: Long,
    id: Long,
) : GuildMessageChannelData(client, guildId, id), TextChannel {
    override fun toString() = "TextChannel($guildId > $id)"
}

interface TextChannel : GuildMessageChannel {
    override val client: KodiClient
    override val id: Long
    override val type: ChannelType
    override val guildId: Long
    override val name: String
    override val position: Int
    override val permissionOverwrites: List<*>
    override val rateLimitPerUser: Int
    override val nsfw: Boolean
    override val topic: String?
    override val lastMessageId: Long?
}

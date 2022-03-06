package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient

class CategoryChannelData(
    client: KodiClient,
    guildId: Long,
    id: Long,
) : GuildChannelData(client, guildId, id), CategoryChannel

interface CategoryChannel : GuildChannel {
    override val client: KodiClient
    override val id: Long
    override val type: ChannelType
    override val guildId: Long
    override val name: String
    override val position: Int
    override val permissionOverwrites: List<*>
}

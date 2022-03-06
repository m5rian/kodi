package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.Guild

open class GuildChannelData(
    client: KodiClient,
    override val guildId: Long,
    id: Long,
) : BaseChannelData(client, id), GuildChannel {
    override lateinit var name: String
    override var position: Int = 0
    override var permissionOverwrites: List<*> = emptyList<Any>()

    override suspend fun guild() =
        client.tempGuildCache[guildId]
}

interface GuildChannel : BaseChannel {
    override val client: KodiClient
    override val id: Long
    override val type: ChannelType
    val name: String
    val guildId: Long
    val position: Int
    val permissionOverwrites: List<*>

    suspend fun guild(): Guild?
}

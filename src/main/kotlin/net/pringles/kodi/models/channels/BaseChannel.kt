package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.ISnowflake

open class BaseChannelData(
    override val client: KodiClient,
    override val id: Long
) : BaseChannel {
    override lateinit var type: ChannelType

    override fun toString() = "Channel($id)"
}

interface BaseChannel : ISnowflake {
    override val client: KodiClient
    override val id: Long
    val type: ChannelType

    override val mention: String get() = "<#$id>"
}

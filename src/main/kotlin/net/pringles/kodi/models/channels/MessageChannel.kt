package net.pringles.kodi.models.channels

import net.pringles.kodi.gateway.KodiClient

interface MessageChannel : BaseChannel {
    override val client: KodiClient
    override val id: Long
    override val type: ChannelType

    suspend fun send(text: String) {
        throw NotImplementedError("Not implemented")
    }
}

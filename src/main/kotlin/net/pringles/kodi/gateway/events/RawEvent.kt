package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.utils.JsonData

data class RawEvent(
    override val client: KodiClient,
    override val type: String,
    val payload: JsonData
) : IEvent

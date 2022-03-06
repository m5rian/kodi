package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.utils.JsonData

interface IEvent {
    val client: KodiClient
    val type: String
}

interface IEventHandler {
    val identifier: String

    suspend fun handle(client: KodiClient, data: JsonData): IEvent
}

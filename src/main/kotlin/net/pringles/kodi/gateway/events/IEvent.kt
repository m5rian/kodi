package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient

interface IEvent {
    val client: KodiClient
    val type: String
}

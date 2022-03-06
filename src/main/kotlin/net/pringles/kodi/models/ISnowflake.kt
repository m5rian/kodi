package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient

interface ISnowflake {
    val client: KodiClient
    val id: Long
    val mention: String
        get() = throw NotImplementedError("Cannot call mention in this object")
}

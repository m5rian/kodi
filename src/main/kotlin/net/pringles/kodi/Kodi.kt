package net.pringles.kodi

import net.pringles.kodi.gateway.ClientConfig
import net.pringles.kodi.gateway.KodiClient

object Kodi {
    const val API_VERSION: Int = 10
    const val GATEWAY_URL: String = "wss://gateway.discord.gg/"

    fun create(clientBuilder: ClientConfig.() -> Unit): KodiClient {
        val builder = ClientConfig().apply(clientBuilder)
        return KodiClient(builder)
    }
}

package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.Guild
import net.pringles.kodi.utils.JsonData

data class GuildCreateEvent(
    override val client: KodiClient,
    override val type: String,
    val guild: Guild
) : IEvent

internal object GuildCreateHandler : IEventHandler {
    override val identifier: String = "GUILD_CREATE"

    override suspend fun handle(client: KodiClient, data: JsonData): GuildCreateEvent {
        val guild = client.modelsBuilder.createGuild(data)
        return GuildCreateEvent(client, identifier, guild)
    }
}

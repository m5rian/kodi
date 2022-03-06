package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.User
import net.pringles.kodi.utils.JsonData

data class ReadyEvent(
    override val client: KodiClient,
    override val type: String,
    val user: User
) : IEvent

internal object ReadyHandler : IEventHandler {
    override val identifier: String = "READY"

    override suspend fun handle(client: KodiClient, data: JsonData): ReadyEvent {
        val user = client.modelsBuilder.createUser(data["user"])
        client.user = user
        return ReadyEvent(client, identifier, user)
    }
}

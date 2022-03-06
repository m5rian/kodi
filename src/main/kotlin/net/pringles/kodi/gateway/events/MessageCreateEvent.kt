package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.Message
import net.pringles.kodi.utils.JsonData

data class MessageCreateEvent(
    override val client: KodiClient,
    override val type: String,
    val message: Message
) : IEvent

internal object MessageCreateHandler : IEventHandler {
    override val identifier: String = "MESSAGE_CREATE"

    override suspend fun handle(client: KodiClient, data: JsonData): MessageCreateEvent {
        val message = client.modelsBuilder.createMessage(data)
        return MessageCreateEvent(client, identifier, message)
    }
}

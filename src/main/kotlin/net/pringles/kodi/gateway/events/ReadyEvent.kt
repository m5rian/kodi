package net.pringles.kodi.gateway.events

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.User
import net.pringles.kodi.models.UserData
import net.pringles.kodi.utils.JsonData

class ReadyEvent(override val client: KodiClient, override val type: String, val user: User) : IEvent

internal object ReadyHandler : IEventHandler {
    override val identifier: String = "READY"

    override suspend fun handle(client: KodiClient, data: JsonData): ReadyEvent {
        val userData = data["user"]

        val user = UserData(client, userData["id"].text().toLong())
        user.name = userData["username"].text()
        user.discriminator = userData["discriminator"].text().toInt()
        user.bot = userData["bot"].bool()
        user.avatarId = userData["avatar"].textOrNull()

        client.user = user
        return ReadyEvent(client, identifier, user)
    }
}

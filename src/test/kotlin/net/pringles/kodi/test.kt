package net.pringles.kodi

import kotlinx.coroutines.runBlocking
import net.pringles.kodi.gateway.events.GuildCreateEvent
import net.pringles.kodi.gateway.events.MessageCreateEvent
import net.pringles.kodi.gateway.events.ReadyEvent
import net.pringles.kodi.gateway.misc.Intent
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Kodi-Test")
fun main() = runBlocking {
    val client = Kodi.create {
        token = System.getenv("TOKEN")
        setIntents(*Intent.ALL)
    }
    client.on<ReadyEvent> {
        logger.debug("${it.type} -> ${it.user.tag} (${it.user.displayAvatarUrl})")
    }
    client.on<GuildCreateEvent> {
        logger.debug("${it.type} -> ${it.guild}")
    }
    client.on<MessageCreateEvent> {
        logger.debug("${it.type} -> ${it.message.author()!!.tag}: ${it.message.content} in ${it.message.channelId}")
    }
    client.login().join()
}

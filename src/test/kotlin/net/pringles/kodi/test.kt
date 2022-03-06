package net.pringles.kodi

import kotlinx.coroutines.runBlocking
import net.pringles.kodi.gateway.events.ReadyEvent
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Kodi-Test")
fun main() = runBlocking {
    val client = Kodi.create { token = System.getenv("TOKEN") }
    client.on<ReadyEvent> {
        logger.debug("${it.type} -> ${it.user}")
        logger.debug("${it.user.tag} (${it.user.displayAvatarUrl})")
    }
    client.login().join()
}

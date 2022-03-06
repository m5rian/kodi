package net.pringles.kodi

import kotlinx.coroutines.runBlocking
import net.pringles.kodi.gateway.events.RawEvent
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Kodi-Test")
fun main() = runBlocking {
    val client = Kodi.create { token = System.getenv("TOKEN") }
    client.on<RawEvent> {
        logger.debug("${it.type} -> ${it.payload}")
    }
    client.login().join()
}

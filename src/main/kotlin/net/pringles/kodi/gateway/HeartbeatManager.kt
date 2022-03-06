package net.pringles.kodi.gateway

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import net.pringles.kodi.gateway.misc.OpCode
import net.pringles.kodi.utils.JsonData
import org.slf4j.LoggerFactory

@Suppress("EXPERIMENTAL_API_USAGE")
internal class HeartbeatManager(private val webSocket: WebSocket) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    var sent: Long = 0L
    var received: Long = 0L

    fun start(interval: Int) {
        webSocket.client.config.scope.launch {
            ticker(interval.toLong(), initialDelayMillis = 0)
                .consumeEach {
                    webSocket.send(OpCode.HEARTBEAT, JsonData.int(webSocket.client.totalEvents))
                    sent = System.nanoTime()
                }
        }
    }

    fun received() {
        received = System.nanoTime()
        logger.trace("Gateway Ping: ${webSocket.client.ping}")
    }

    fun ping(): Long = received - sent
}

package net.pringles.kodi.gateway

import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readBytes
import io.ktor.http.cio.websocket.send
import kotlinx.coroutines.flow.consumeAsFlow
import net.pringles.kodi.gateway.events.RawEvent
import net.pringles.kodi.gateway.misc.Intent
import net.pringles.kodi.gateway.misc.OpCode
import net.pringles.kodi.utils.JsonData
import org.slf4j.LoggerFactory
import java.nio.charset.StandardCharsets

internal class WebSocket(val client: KodiClient, val session: DefaultWebSocketSession) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    val heartbeatManager = HeartbeatManager(this)

    suspend fun send(op: Int, data: JsonData) {
        val json = JsonData.empty()
            .with("op", op)
            .with("d", data)
        session.send(json.toString())
        logger.trace("Kodi -> $json")
    }

    suspend fun start() {
        login()
        session.incoming.consumeAsFlow().collect {
            if (it !is Frame.Text && it !is Frame.Binary) return@collect
            val data = JsonData.create(it.readBytes().toString(StandardCharsets.UTF_8))
            onEvent(data)
        }
    }

    private suspend fun login() {
        val payload = JsonData.empty()
            .with("token", client.config.token)
            .with("intents", Intent.bitmask(client.config.intentsConfig.intents))
            .with(
                "properties",
                JsonData.empty()
                    .with("\$os", System.getProperty("os.name"))
                    .with("\$browser", "Kodi")
                    .with("\$device", "Kodi")
            )
        send(OpCode.IDENTIFY, payload)
    }

    private suspend fun onEvent(data: JsonData) {
        logger.trace("Kodi <- $data")
        if (data.hasValue("s")) client.totalEvents = data["s"].int()

        val op = data["op"].int()
        val payload = data["d"]

        when (op) {
            OpCode.DISPATCH -> onDispatch(data)
            OpCode.HEARTBEAT -> send(OpCode.HEARTBEAT, JsonData.int(client.totalEvents))
            OpCode.HELLO -> heartbeatManager.start(payload["heartbeat_interval"].int())
            OpCode.HEARTBEAT_ACK -> heartbeatManager.received()
        }
    }

    private suspend fun onDispatch(data: JsonData) {
        logger.trace("Kodi <- $data")
        val type = data["t"].text()

        client.events.emit(RawEvent(client, type, data["d"]))
    }
}

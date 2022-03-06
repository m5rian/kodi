package net.pringles.kodi.gateway

import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.pringles.kodi.Kodi
import net.pringles.kodi.gateway.events.IEvent
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

class KodiClient(val config: ClientConfig) {
    private val httpClient = config.httpClient
    private lateinit var webSocket: WebSocket

    val events = MutableSharedFlow<IEvent>()
    val ping: Duration get() = webSocket.heartbeatManager.ping().nanoseconds
    var totalEvents: Int? = null
        internal set

    fun login() = config.scope.launch {
        connect()
    }

    inline fun <reified T> on(crossinline action: suspend (event: T) -> Unit) {
        events.filterIsInstance<T>()
            .onEach { action(it) }
            .launchIn(config.scope)
    }

    private suspend fun connect() {
        val session = httpClient.webSocketSession {
            url {
                url.takeFrom(Kodi.GATEWAY_URL)
                parameter("v", Kodi.API_VERSION)
                parameter("encoding", "json")
            }
        }
        webSocket = WebSocket(this, session)
        webSocket.start()
    }
}

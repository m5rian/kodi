package net.pringles.kodi.gateway

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import net.pringles.kodi.gateway.misc.Intent

@Suppress("EXPERIMENTAL_API_USAGE")
class ClientConfig(
    var token: String = "",
    var intentsConfig: IntentsConfig = IntentsConfig(),
    var httpClient: HttpClient = HttpClient(CIO) { install(WebSockets) },
    var scope: CoroutineScope = GlobalScope,
) {
    fun intents(vararg intents: Intent) {
        intentsConfig.intents.addAll(intents)
    }

    fun intents(intents: Collection<Intent>) {
        intentsConfig.intents.addAll(intents)
    }

    fun intents(builder: IntentsConfig.() -> Unit) {
        intentsConfig = IntentsConfig().apply(builder)
    }
}

class IntentsConfig(
    var intents: MutableSet<Intent> = Intent.DEFAULT,
) {
    operator fun Intent.unaryPlus() {
        intents.add(this)
    }

    operator fun Collection<Intent>.unaryPlus() {
        intents.addAll(this)
    }
}

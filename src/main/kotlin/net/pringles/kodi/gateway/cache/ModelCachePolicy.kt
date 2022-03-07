package net.pringles.kodi.gateway.cache

import kotlinx.coroutines.launch
import net.pringles.kodi.gateway.KodiClient

internal class ModelCachePolicy<K, V>(
    private val client: KodiClient,
    private val onView: ViewCache<V>?,
    private val onGet: GetCache<K, V>?,
    private val onUpdate: UpdateCache<V>?,
    private val onRemove: RemoveCache<K>?,
) {
    suspend fun view() = onView?.invoke() ?: emptyList()
    suspend fun get(key: K) = onGet?.invoke(key)

    fun update(value: V) {
        onUpdate ?: return
        client.config.scope.launch { onUpdate.invoke(value) }
    }

    fun remove(key: K) {
        onRemove ?: return
        client.config.scope.launch { onRemove.invoke(key) }
    }
}

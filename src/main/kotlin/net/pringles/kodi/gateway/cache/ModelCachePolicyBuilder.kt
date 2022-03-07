package net.pringles.kodi.gateway.cache

import net.pringles.kodi.gateway.KodiClient

typealias ViewCache<V> = suspend () -> List<V>
typealias GetCache<K, V> = suspend (K) -> V?
typealias UpdateCache<V> = suspend (V) -> Unit
typealias RemoveCache<K> = suspend (K) -> Unit

abstract class ModelCachePolicyBuilder<K, V>(
    internal var onView: ViewCache<V>? = null,
    internal var onGet: GetCache<K, V>? = null,
    internal var onUpdate: UpdateCache<V>? = null,
    internal var onRemove: RemoveCache<K>? = null
) {
    fun view(action: ViewCache<V>) {
        onView = action
    }

    fun get(action: GetCache<K, V>) {
        onGet = action
    }

    fun update(action: UpdateCache<V>) {
        onUpdate = action
    }

    fun remove(action: RemoveCache<K>) {
        onRemove = action
    }

    internal fun build(client: KodiClient) = ModelCachePolicy(client, onView, onGet, onUpdate, onRemove)
}
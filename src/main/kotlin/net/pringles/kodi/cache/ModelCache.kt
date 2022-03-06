package net.pringles.kodi.cache

class ModelCache<K, V>(
    var getter: suspend (K) -> V? = { null },
    var setter: suspend (K, V) -> Unit = { _, _ -> },
    var deletion: suspend (K) -> Unit = { }
) {

    fun get(action: suspend (K) -> V?) {
        getter = action
    }

    fun set(action: suspend (K, V) -> Unit) {
        setter = action
    }

    fun delete(action: suspend (K) -> Unit) {
        deletion = action
    }

}
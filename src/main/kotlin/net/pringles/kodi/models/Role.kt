package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient

data class RoleData(
    override val client: KodiClient,
    override val id: Long,
    override val guildId: Long,
) : Role {
    override lateinit var name: String
    override var color: Int = 0
    override var position: Int = 0
    override var iconId: String? = null
    override var permissions: Long = 0L
    override var managed: Boolean = false
    override var hoist: Boolean = false
    override var mentionable: Boolean = false

    override suspend fun guild() =
        client.tempGuildCache[guildId]

    override fun toString() = "Role($guildId > $id)"
}

interface Role : ISnowflake {
    override val client: KodiClient
    override val id: Long
    val guildId: Long
    val name: String
    val color: Int
    val position: Int
    val iconId: String?
    val permissions: Long
    val managed: Boolean
    val hoist: Boolean
    val mentionable: Boolean

    val iconUrl: String? get() = iconId?.let { ICON_URL.format(id, it) }

    suspend fun guild(): Guild?

    override val mention: String get() = "<@&$id>"

    companion object {
        const val ICON_URL = "https://cdn.discordapp.com/role-icons/%s/%s.png"
    }
}

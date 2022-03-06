package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient

data class RoleData(
    override val client: KodiClient,
    override val guild: Guild,
    override val id: Long,
) : Role {
    override lateinit var name: String
    override var color: Int = 0
    override var position: Int = 0
    override var iconId: String? = null
    override var permissions: Long = 0L
    override var managed: Boolean = false
    override var hoist: Boolean = false
    override var mentionable: Boolean = false

    override fun toString() = "Role(${guild.id} > $id)"
}

interface Role {
    val client: KodiClient
    val guild: Guild
    val id: Long
    val name: String
    val color: Int
    val position: Int
    val iconId: String?
    val permissions: Long
    val managed: Boolean
    val hoist: Boolean
    val mentionable: Boolean

    val iconUrl: String?
        get() = iconId?.let { ICON_URL.format(id, it) }

    companion object {
        const val ICON_URL = "https://cdn.discordapp.com/role-icons/%s/%s.png"
    }
}

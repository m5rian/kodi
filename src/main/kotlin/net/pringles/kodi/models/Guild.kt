package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient

data class GuildData(
    override val client: KodiClient,
    override val id: Long,
) : Guild {
    override lateinit var name: String
    override var ownerId: Long = 0
    override var memberCount: Int = 0
    override var iconId: String? = null
    override var members: List<Member> = emptyList()
    override var roles: List<Role> = emptyList()

    override fun toString() = "Guild($id)"
}

interface Guild {
    val client: KodiClient
    val id: Long
    val name: String
    val ownerId: Long
    val memberCount: Int
    val iconId: String?
    val members: List<Member>
    val roles: List<Role>

    val owner: Member?
        get() = members.firstOrNull { it.id == ownerId }

    val iconUrl: String?
        get() = iconId?.let { ICON_URL.format(id, it, if (it.startsWith("a_")) "gif" else "png") }

    val publicRole: Role
        get() = roles.first { it.id == id }

    companion object {
        const val ICON_URL = "https://cdn.discordapp.com/icons/%s/%s.%s"
    }
}

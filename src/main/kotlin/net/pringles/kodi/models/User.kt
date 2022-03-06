package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient

data class UserData(
    override val client: KodiClient,
    override val id: Long,
) : User {
    override lateinit var name: String
    override var discriminator: Int = 0
    override var bot: Boolean = false
    override var avatarId: String? = null
    override var system: Boolean = false

    override fun toString() = "User($id)"
}

interface User {
    val client: KodiClient
    val id: Long
    val name: String
    val discriminator: Int
    val bot: Boolean
    val avatarId: String?
    val system: Boolean

    val tag: String
        get() = "$name#$discriminator"

    val avatarUrl: String?
        get() =
            if (avatarId == null) null
            else AVATAR_URL.format(id, avatarId!!, if (avatarId!!.startsWith("a_")) "gif" else "png")

    val displayAvatarUrl: String
        get() = avatarUrl ?: DEFAULT_AVATAR_URL.format(discriminator % 5)

    companion object {
        const val AVATAR_URL = "https://cdn.discordapp.com/avatars/%s/%s.%s"
        const val DEFAULT_AVATAR_URL = "https://cdn.discordapp.com/embed/avatars/%s.png"
    }
}

package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import java.time.OffsetDateTime

data class MemberData(
    override val client: KodiClient,
    override val guild: Guild,
    override val user: User,
) : Member {
    override var roles: List<Role> = emptyList()
    override var mute: Boolean = false
    override var deaf: Boolean = false
    override lateinit var joinedAt: OffsetDateTime

    override fun toString() = "Member(${guild.id} > ${user.id})"
}

interface Member {
    val client: KodiClient
    val guild: Guild
    val user: User
    val roles: List<Role>
    val mute: Boolean
    val deaf: Boolean
    val joinedAt: OffsetDateTime

    val id: Long
        get() = user.id
}

package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import java.time.OffsetDateTime

data class MemberData(
    override val client: KodiClient,
    override val guildId: Long,
    override val user: User,
) : Member {
    override var roleIds: List<Long> = emptyList()
    override var mute: Boolean = false
    override var deaf: Boolean = false
    override lateinit var joinedAt: OffsetDateTime

    override suspend fun guild() = client.guildCache.get(guildId)
    override suspend fun roles() = client.roleCache.view()
        .filter { it.id in roleIds }
        .toMutableList()
        .apply {
            val publicRole = guild()?.publicRole() ?: return@apply
            add(publicRole)
        }

    override fun toString() = "Member(${guildId} > ${user.id})"
}

interface Member : ISnowflake {
    override val client: KodiClient
    val guildId: Long
    val roleIds: List<Long>
    val user: User
    val mute: Boolean
    val deaf: Boolean
    val joinedAt: OffsetDateTime

    override val id: Long get() = user.id

    suspend fun guild(): Guild?
    suspend fun roles(): List<Role>

    override val mention: String
        get() = "<@!$id>"
}

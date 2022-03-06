package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.channels.GuildChannel

data class GuildData(
    override val client: KodiClient,
    override val id: Long,
) : Guild {
    override lateinit var name: String
    override var ownerId: Long = 0
    override var memberCount: Int = 0
    override var iconId: String? = null
    override var channelIds: List<Long> = emptyList()
    override var roleIds: List<Long> = emptyList()

    override suspend fun channels() =
        client.tempChannelCache.values
            .filterIsInstance<GuildChannel>()
            .filter { it.guildId == id }

    override suspend fun roles() =
        client.tempRoleCache.values
            .filter { it.guildId == id }

    override suspend fun members() =
        client.tempMemberCache.values
            .filter { it.guildId == id }

    override suspend fun owner() = client.tempMemberCache[ownerId]
    override suspend fun publicRole() = client.tempRoleCache[ownerId]

    override fun toString() = "Guild($id)"
}

interface Guild : ISnowflake {
    override val client: KodiClient
    override val id: Long
    val name: String
    val ownerId: Long
    val memberCount: Int
    val iconId: String?
    val channelIds: List<Long>
    val roleIds: List<Long>

    val iconUrl: String? get() = iconId?.let { ICON_URL.format(id, it, if (it.startsWith("a_")) "gif" else "png") }

    suspend fun channels(): List<GuildChannel>
    suspend fun roles(): List<Role>
    suspend fun members(): List<Member>

    suspend fun owner(): Member?
    suspend fun publicRole(): Role?

    companion object {
        const val ICON_URL = "https://cdn.discordapp.com/icons/%s/%s.%s"
    }
}

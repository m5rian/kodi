package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.utils.JsonData

internal class ModelsBuilder(private val client: KodiClient) {
    fun createUser(data: JsonData): UserData {
        val user = UserData(client, data["id"].asLong())
        user.name = data["username"].text()
        user.discriminator = data["discriminator"].int()
        user.bot = data["bot"].bool()
        user.avatarId = data["avatar"].textOrNull()
        return user
    }

    fun createGuild(data: JsonData): GuildData {
        val guild = GuildData(client, data["id"].asLong())
        guild.name = data["name"].text()
        guild.ownerId = data["owner_id"].asLong()
        guild.memberCount = data["member_count"].int()
        guild.iconId = data["icon_id"].textOrNull()
        guild.roles = data["roles"].list().map { createRole(it, guild) }
        guild.members = data["members"].list().map { createMember(it, guild, it["user"]) }
        return guild
    }

    fun createMember(memberData: JsonData, guildData: GuildData, userData: JsonData): MemberData {
        val user = createUser(userData)
        val member = MemberData(client, guildData, user)
        member.roles = memberData["roles"].list().map { guildData.roles.first { role -> role.id == it.asLong() } }
        member.mute = memberData["mute"].bool()
        member.deaf = memberData["deaf"].bool()
        member.joinedAt = memberData["joined_at"].time()
        return member
    }

    fun createRole(roleData: JsonData, guildData: GuildData): RoleData {
        val role = RoleData(client, guildData, roleData["id"].asLong())
        role.name = roleData["name"].text()
        role.color = roleData["color"].int()
        role.position = roleData["position"].int()
        role.iconId = roleData["icon"].textOrNull()
        role.permissions = roleData["permissions"].asLong()
        role.managed = roleData["managed"].bool()
        role.hoist = roleData["hoist"].bool()
        role.mentionable = roleData["mentionable"].bool()
        return role
    }
}

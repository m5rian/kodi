package net.pringles.kodi.models

import net.pringles.kodi.gateway.KodiClient
import net.pringles.kodi.models.channels.BaseChannelData
import net.pringles.kodi.models.channels.CategoryChannelData
import net.pringles.kodi.models.channels.ChannelType
import net.pringles.kodi.models.channels.GuildChannelData
import net.pringles.kodi.models.channels.NewsChannelData
import net.pringles.kodi.models.channels.TextChannelData
import net.pringles.kodi.utils.JsonData

internal class ModelsBuilder(private val client: KodiClient) {
    fun createUser(data: JsonData): UserData {
        val user = UserData(client, data["id"].asLong())
        user.name = data["username"].text()
        user.discriminator = data["discriminator"].asInt()
        user.bot = data["bot"].boolOrNull() ?: false
        user.avatarId = data["avatar"].textOrNull()
        client.tempUserCache[user.id] = user
        return user
    }

    fun createGuild(data: JsonData): GuildData {
        val guild = GuildData(client, data["id"].asLong())
        guild.name = data["name"].text()
        guild.ownerId = data["owner_id"].asLong()
        guild.memberCount = data["member_count"].int()
        guild.iconId = data["icon_id"].textOrNull()
        guild.channelIds = data["channels"].list().map { createGuildChannel(it, guild).id }
        guild.roleIds = data["roles"].list().map { createRole(it, guild).id }
        data["members"].list().forEach { createMember(it, guild.id, createUser(it["user"])).id }
        client.tempGuildCache[guild.id] = guild
        return guild
    }

    fun createRole(roleData: JsonData, guildData: GuildData): RoleData {
        val role = RoleData(client, roleData["id"].asLong(), guildData.id)
        role.name = roleData["name"].text()
        role.color = roleData["color"].int()
        role.position = roleData["position"].int()
        role.iconId = roleData["icon"].textOrNull()
        role.permissions = roleData["permissions"].asLong()
        role.managed = roleData["managed"].bool()
        role.hoist = roleData["hoist"].bool()
        role.mentionable = roleData["mentionable"].bool()
        client.tempRoleCache[role.id] = role
        return role
    }

    fun createMember(memberData: JsonData, guildId: Long, user: UserData): MemberData {
        val member = MemberData(client, guildId, user)
        member.roleIds = memberData["roles"].list().map { it.asLong() }
        member.mute = memberData["mute"].bool()
        member.deaf = memberData["deaf"].bool()
        member.joinedAt = memberData["joined_at"].time()
        client.tempMemberCache[member.id] = member
        return member
    }

    fun createMessage(messageData: JsonData): MessageData {
        val message = MessageData(client, messageData["id"].asLong())
        val author = createUser(messageData["author"])
        message.content = messageData["content"].text()
        message.channelId = messageData["channel_id"].asLong()
        message.authorId = author.id
        message.timestamp = messageData["timestamp"].time()
        if (messageData.hasValue("member")) {
            message.guildId = messageData["guild_id"].asLong()
            createMember(messageData["member"], message.guildId, author)
        }
        return message
    }

    fun createDMChannel(channelData: JsonData): BaseChannelData {
        val channel = BaseChannelData(client, channelData["id"].asLong())
        channel.type = ChannelType.values().firstOrNull { it.id == channelData["type"].int() } ?: ChannelType.UNKNOWN
        client.tempChannelCache[channel.id] = channel
        return channel
    }

    fun createGuildChannel(channelData: JsonData, guildData: GuildData): BaseChannelData {
        val type = ChannelType.values()
            .firstOrNull { it.id == channelData["type"].int() }
            ?: ChannelType.UNKNOWN
        val id = channelData["id"].asLong()
        return when (type) {
            ChannelType.UNKNOWN -> throw NotImplementedError("Unexpected channel type")
            ChannelType.GUILD_TEXT -> {
                val channel = TextChannelData(client, guildData.id, id)
                channel.name = channelData["name"].text()
                channel.position = channelData["position"].int()
                channel.permissionOverwrites = channelData["permission_overwrites"].list()
                channel.parentId = channelData["parent_id"].asLong()
                channel.rateLimitPerUser = channelData["rate_limit_per_user"].int()
                channel.nsfw = channelData["nsfw"].boolOrNull() ?: false
                channel.topic = channelData["topic"].textOrNull()
                channel.lastMessageId = channelData["last_message_id"].asLong()
                client.tempChannelCache[channel.id] = channel
                channel
            }
            ChannelType.GUILD_NEWS -> {
                val channel = NewsChannelData(client, guildData.id, id)
                channel.name = channelData["name"].text()
                channel.position = channelData["position"].int()
                channel.permissionOverwrites = channelData["permission_overwrites"].list()
                channel.parentId = channelData["parent_id"].asLong()
                channel.rateLimitPerUser = channelData["rate_limit_per_user"].int()
                channel.nsfw = channelData["nsfw"].boolOrNull() ?: false
                channel.topic = channelData["topic"].textOrNull()
                channel.lastMessageId = channelData["last_message_id"].asLong()
                client.tempChannelCache[channel.id] = channel
                channel
            }
            ChannelType.GUILD_CATEGORY -> {
                val channel = CategoryChannelData(client, guildData.id, id)
                channel.name = channelData["name"].text()
                channel.position = channelData["position"].int()
                channel.permissionOverwrites = channelData["permission_overwrites"].list()
                client.tempChannelCache[channel.id] = channel
                channel
            }
            else -> {
                val channel = GuildChannelData(client, guildData.id, channelData["id"].asLong())
                channel.type = type
                channel.name = channelData["name"].text()
                channel.position = channelData["position"].int()
                channel.permissionOverwrites = channelData["permission_overwrites"].list()
                client.tempChannelCache[channel.id] = channel
                channel
            }
        }
    }
}

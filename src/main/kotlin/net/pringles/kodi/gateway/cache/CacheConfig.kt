package net.pringles.kodi.gateway.cache

import net.pringles.kodi.gateway.cache.modelCaches.*
import net.pringles.kodi.models.Member

class CacheConfig(
    var userCachePolicy: UserCache = DefaultCachePolicy.user(),
    var guildCachePolicy: GuildCache = DefaultCachePolicy.guild(),
    var channelCachePolicy: ChannelCache = DefaultCachePolicy.channel(),
    var roleCachePolicy: RoleCache = DefaultCachePolicy.role(),
    var memberCachePolicy: MemberCache = DefaultCachePolicy.member(),
) {
    fun user(builder: UserCache.() -> Unit) {
        userCachePolicy = UserCache().apply(builder)
    }

    fun guild(builder: GuildCache.() -> Unit) {
        guildCachePolicy = GuildCache().apply(builder)
    }

    fun channel(builder: ChannelCache.() -> Unit) {
        channelCachePolicy = ChannelCache().apply(builder)
    }

    fun role(builder: RoleCache.() -> Unit) {
        roleCachePolicy = RoleCache().apply(builder)
    }

    fun member(builder: ModelCachePolicyBuilder<MemberCacheKey, Member>.() -> Unit) {
        memberCachePolicy = MemberCache().apply(builder)
    }
}

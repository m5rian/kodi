package net.pringles.kodi.gateway.cache

import net.pringles.kodi.models.Guild
import net.pringles.kodi.models.Member
import net.pringles.kodi.models.Role
import net.pringles.kodi.models.User
import net.pringles.kodi.models.channels.BaseChannel

class CacheConfig(
    var userCachePolicy: ModelCachePolicyBuilder<Long, User> = DefaultCachePolicy.user(),
    var guildCachePolicy: ModelCachePolicyBuilder<Long, Guild> = DefaultCachePolicy.guild(),
    var channelCachePolicy: ModelCachePolicyBuilder<Long, BaseChannel> = DefaultCachePolicy.channel(),
    var roleCachePolicy: ModelCachePolicyBuilder<Long, Role> = DefaultCachePolicy.role(),
    var memberCachePolicy: ModelCachePolicyBuilder<MemberCacheKey, Member> = DefaultCachePolicy.member(),
) {
    fun user(builder: ModelCachePolicyBuilder<Long, User>.() -> Unit) {
        userCachePolicy = ModelCachePolicyBuilder<Long, User>().apply(builder)
    }

    fun guild(builder: ModelCachePolicyBuilder<Long, Guild>.() -> Unit) {
        guildCachePolicy = ModelCachePolicyBuilder<Long, Guild>().apply(builder)
    }

    fun channel(builder: ModelCachePolicyBuilder<Long, BaseChannel>.() -> Unit) {
        channelCachePolicy = ModelCachePolicyBuilder<Long, BaseChannel>().apply(builder)
    }

    fun role(builder: ModelCachePolicyBuilder<Long, Role>.() -> Unit) {
        roleCachePolicy = ModelCachePolicyBuilder<Long, Role>().apply(builder)
    }

    fun member(builder: ModelCachePolicyBuilder<MemberCacheKey, Member>.() -> Unit) {
        memberCachePolicy = ModelCachePolicyBuilder<MemberCacheKey, Member>().apply(builder)
    }
}

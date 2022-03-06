package net.pringles.kodi.cache

import net.pringles.kodi.models.Guild
import net.pringles.kodi.models.Member

class CacheConfig(
    var guild: ModelCache<Long, Guild> = DefaultCache.guild(),
    var member: ModelCache<Long, Member> = DefaultCache.member()
) {

    fun guild(builder: ModelCache<Long, Guild>.() -> Unit) {
        guild = ModelCache<Long, Guild>().apply(builder)
    }

    fun member(builder: ModelCache<Long, Member>.() -> Unit) {
        member = ModelCache<Long, Member>().apply(builder)
    }

}
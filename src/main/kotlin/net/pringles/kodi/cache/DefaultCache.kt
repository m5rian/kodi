package net.pringles.kodi.cache

import net.pringles.kodi.models.Guild
import net.pringles.kodi.models.Member

object DefaultCache {
    private val guildCache = mutableMapOf<Long, Guild>()
    private val memberCache = mutableMapOf<Long, Member>()

    fun guild(): ModelCache<Long, Guild> =
        ModelCache<Long, Guild>().apply {
            get { id -> guildCache[id] }
            set { id, guild -> guildCache[id] = guild }
            delete { id -> guildCache.remove(id) }
        }

    fun member(): ModelCache<Long, Member> =
        ModelCache<Long, Member>().apply {
            get { id -> memberCache[id] }
            set { id, member -> memberCache[id] = member }
            delete { id -> memberCache.remove(id) }
        }

}
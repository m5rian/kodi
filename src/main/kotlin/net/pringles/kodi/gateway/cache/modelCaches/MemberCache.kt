package net.pringles.kodi.gateway.cache.modelCaches

import net.pringles.kodi.gateway.cache.MemberCacheKey
import net.pringles.kodi.gateway.cache.ModelCachePolicyBuilder
import net.pringles.kodi.models.Member

class MemberCache : ModelCachePolicyBuilder<MemberCacheKey, Member>()
package net.pringles.kodi.gateway.cache.modelCaches

import net.pringles.kodi.gateway.cache.ModelCachePolicyBuilder
import net.pringles.kodi.models.User

class UserCache : ModelCachePolicyBuilder<Long, User>()
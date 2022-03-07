package net.pringles.kodi.gateway.cache.modelCaches

import net.pringles.kodi.gateway.cache.ModelCachePolicyBuilder
import net.pringles.kodi.models.Role

class RoleCache(
    internal var onGetByGuildId: ((Long) -> List<Role>)? = null
) : ModelCachePolicyBuilder<Long, Role>() {


    fun getByGuildId(action: (Long) -> List<Role>) {
        onGetByGuildId = action
    }

}
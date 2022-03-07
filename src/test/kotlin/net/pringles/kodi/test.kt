package net.pringles.kodi

import kotlinx.coroutines.runBlocking
import net.pringles.kodi.gateway.cache.DefaultCachePolicy
import net.pringles.kodi.gateway.events.GuildCreateEvent
import net.pringles.kodi.gateway.events.MessageCreateEvent
import net.pringles.kodi.gateway.events.ReadyEvent
import net.pringles.kodi.gateway.misc.Intent
import net.pringles.kodi.models.Role
import net.pringles.kodi.models.channels.GuildChannel
import net.pringles.kodi.models.channels.GuildMessageChannel
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

private val logger = LoggerFactory.getLogger("Kodi-Test")
fun main() = runBlocking {
    val roleCache = ConcurrentHashMap<Long, Role>()
    val client = Kodi.create {
        token = System.getenv("TOKEN")
        intents(*Intent.DEFAULT, Intent.GUILD_MESSAGE_CONTENT)
        cache {
            channelCachePolicy = DefaultCachePolicy.none()
            role {
                view { roleCache.values.toList() }
                get { roleCache[it] }
                update { if (it.id == it.guildId) roleCache[it.id] = it }
                remove { roleCache.remove(it) }
                getByGuildId {
                    emptyList()
                }
            }
        }
    }
    client.on<ReadyEvent> {
        logger.debug("${it.type} -> ${it.user.tag} (${it.user.displayAvatarUrl})")
    }
    client.on<GuildCreateEvent> {
        logger.debug("${it.type} -> ${it.guild}")
    }
    client.on<MessageCreateEvent> {
        val message = it.message
        val user = message.author()
        val channel = message.channel() as? GuildMessageChannel
        val member = message.member()
        val guild = message.guild()
        println("User: ${user?.tag} (${user?.id})")
        println("Channel: ${channel?.name} (${channel?.id})")
        println("Member: ${member?.user?.tag} (${member?.id})")
        println("Member Roles: [${member?.roles()?.joinToString(", ") { r -> "${r.name} (${r.id})" }}]")
        println("Guild: ${guild?.name} (${guild?.id})")
        println("Guild Channels: [${
            client.channelCache.view()
                .filterIsInstance<GuildChannel>()
                .filter { c -> c.guildId == guild?.id }
                .joinToString(", ") { c -> "${c.name} (${c.id})" }
        }]"
        )
    }
    client.login().join()
}

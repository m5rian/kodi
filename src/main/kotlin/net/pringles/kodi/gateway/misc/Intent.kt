package net.pringles.kodi.gateway.misc

enum class Intent(val bitCount: Int, val privileged: Boolean, val guild: Boolean) {
    GUILDS(0, false, true),
    GUILD_MEMBERS(1, true, true),
    GUILD_BANS(2, false, true),
    GUILD_EMOJIS_AND_STICKERS(3, false, true),
    GUILD_INTEGRATIONS(4, false, true),
    GUILD_WEBHOOKS(5, false, true),
    GUILD_INVITES(6, false, true),
    GUILD_VOICE_STATES(7, false, true),
    GUILD_PRESENCES(8, true, true),
    GUILD_MESSAGES(9, false, true),
    GUILD_MESSAGES_REACTIONS(10, false, true),
    GUILD_MESSAGE_TYPING(11, false, true),
    DIRECT_MESSAGES(12, false, false),
    DIRECT_MESSAGE_REACTIONS(13, false, false),
    DIRECT_MESSAGE_TYPING(14, false, false),
    GUILD_MESSAGE_CONTENT(15, true, true),
    GUILD_SCHEDULED_EVENTS(16, false, true);

    val value = 1 shl bitCount

    companion object {
        val ALL = values()
        val DEFAULT = ALL.filterNot { it.privileged }.toTypedArray()
        val NONE = emptyArray<Intent>()

        fun bitmask(intents: Collection<Intent>) =
            intents.map { it.value }.reduce { acc, i -> acc or i }

        fun fromBitmask(bitmask: Int) =
            ALL.filter { it.value and bitmask != 0 }
    }
}

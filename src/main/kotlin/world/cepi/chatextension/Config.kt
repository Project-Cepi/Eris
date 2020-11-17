package world.cepi.chatextension

data class DiscordConfig(
    val enabled: Boolean = false,
    val token: String = "",
    val channel: Long = 0,
    val console: Boolean = false,
    val consoleChannel: Long = 0,
    val inviteLink: String = "https://discord.cepi.world"
)
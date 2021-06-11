package world.cepi.chatextension

data class DiscordConfig(
    val enabled: Boolean = false,
    val token: String = "",
    val channel: Long = 0,
    val inviteLink: String = "https://discord.cepi.world"
)
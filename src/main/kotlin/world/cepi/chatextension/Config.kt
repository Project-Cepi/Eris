package world.cepi.chatextension

import com.beust.klaxon.Klaxon
import java.io.File

data class DiscordConfig(
    val enabled: Boolean = false,
    val token: String = "",
    val channel: Int = 0,
    val console: Boolean = false,
    val consoleChannel: Int = 0
)
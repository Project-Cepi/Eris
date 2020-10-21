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
{
    companion object {
        fun get(): DiscordConfig {
            val configFile = File("./discord-config.json")
            val klaxon = Klaxon()

            return if (!configFile.exists()) {
                configFile.writeText(klaxon.toJsonString(DiscordConfig()))
                DiscordConfig()
            } else klaxon.parse<DiscordConfig>(configFile.readText())!!

        }
    }
}
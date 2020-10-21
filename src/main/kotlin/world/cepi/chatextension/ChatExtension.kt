package world.cepi.chatextension

import com.beust.klaxon.Klaxon
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import java.io.File

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
        logger.info("[ChatExtension] Your discord bot can be invited with this link: ${discord?.createBotInvite()}")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

    val config: DiscordConfig
    get() {
        val configFile = File("./discord-config.json")
        val klaxon = Klaxon()

        return if (!configFile.exists()) {
            configFile.writeText(klaxon.toJsonString(DiscordConfig()))
            DiscordConfig()
        } else klaxon.parse<DiscordConfig>(configFile.readText())!!
    }

    val discord: DiscordApi? = if (config.enabled) DiscordApiBuilder().setToken(config.token).login().join() else null
}
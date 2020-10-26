package world.cepi.chatextension

import com.beust.klaxon.Klaxon
import com.google.gson.Gson
import com.google.gson.JsonParser
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import java.io.File

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ChatExtension] has been enabled!")
		 logger.info("[ChatExtension] Your discord bot can be invited with this link: ${discord?.createBotInvite()}")
    }

    override fun terminate() {
        logger.info("[ChatExtension] has been disabled!")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

    companion object {
        val config: DiscordConfig
            get() {
                val configFile = File("./discord-config.json")
                val gson = Gson()

                return if (!configFile.exists()) {
                    configFile.writeText(gson.toJson(DiscordConfig()))
                    DiscordConfig()
                } else gson.fromJson(configFile.reader(), DiscordConfig::class.java)
            }

        val discord: DiscordApi? = if (config.enabled) DiscordApiBuilder().setToken(config.token).login().join() else null

    }
}
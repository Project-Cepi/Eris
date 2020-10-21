package world.cepi.chatextension

import com.beust.klaxon.Klaxon
import net.minestom.server.extensions.Extension;
import java.io.File

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
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
}
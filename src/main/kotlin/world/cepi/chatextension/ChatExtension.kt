package world.cepi.chatextension

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.extensions.Extension
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import world.cepi.chatextension.discord.DiscordToChat
import world.cepi.chatextension.discord.chatToDiscord
import java.io.File

class ChatExtension : Extension() {

    private val connectionManager = MinecraftServer.getConnectionManager()

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
        logger.info("[ChatExtension] Your discord bot can be invited with this link: ${discord?.createBotInvite()}")

        MinecraftServer.getConnectionManager().addPlayerInitialization { player -> player.addEventCallback(PlayerChatEvent::class.java) { event -> chatToDiscord(event)} }

        discord?.addListener(DiscordToChat())
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

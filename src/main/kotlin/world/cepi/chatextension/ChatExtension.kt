package world.cepi.chatextension

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import world.cepi.chatextension.discord.chatToDiscord
import world.cepi.chatextension.discord.onJoin
import world.cepi.chatextension.discord.onLeave
import java.io.File

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ChatExtension] has been enabled!")
        if (config.enabled) {
            logger.info("[ChatExtension] Your discord bot can be invited with this link: ${discord?.createBotInvite()}")
        }
        registerEvents()
    }

    override fun terminate() {
        logger.info("[ChatExtension] has been disabled!")
    }

    private fun registerEvents() {
        val connectionManager = MinecraftServer.getConnectionManager()

        connectionManager.addPlayerInitialization {player ->
            player.addEventCallback(PlayerChatEvent::class.java) {event -> chatToDiscord(event)}
            onJoin(player)
            player.addEventCallback(PlayerDisconnectEvent::class.java) {event -> onLeave(event.player)}
        }
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

        val discordPrefix = "${ChatColor.PURPLE}[DISCORD]"

    }
}
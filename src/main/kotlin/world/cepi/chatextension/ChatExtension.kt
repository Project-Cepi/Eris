package world.cepi.chatextension

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.JsonMessage
import net.minestom.server.chat.RichMessage
import net.minestom.server.event.PlayerEvent
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.channel.ChannelType
import org.javacord.api.entity.channel.ServerTextChannel
import org.javacord.api.entity.server.invite.Invite
import org.javacord.api.entity.server.invite.InviteBuilder
import world.cepi.chatextension.discord.*
import world.cepi.chatextension.events.styleFormattedChat
import world.cepi.kstom.addEventCallback
import world.cepi.kstom.asColored
import world.cepi.kstom.asRich
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
        connectionManager.addPlayerInitialization { player ->

            player.addEventCallback(PlayerLoginEvent::class) {
                MinecraftServer.getConnectionManager().broadcastMessage(
                        "§a§lJOIN §r§8| §7${player.username}".asColored().asRich()
                )
            }

            player.addEventCallback(PlayerChatEvent::class) {event ->
                chatToDiscord(event)
                styleFormattedChat(event)
            }

            onJoin(player)

            player.addEventCallback(PlayerDisconnectEvent::class) { event ->
                onLeave(event.player)

                MinecraftServer.getConnectionManager().broadcastMessage(
                        "§c§lLEAVE §r§8| §7${event.player.username}".asColored().asRich()
                )

            }
        }

        if (discord != null) {
            discord.addMessageCreateListener(DiscordToChat())
            discord.addServerMemberJoinListener(OnJoin())
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

        private fun getDiscordChannel(id: Long): ServerTextChannel? {
            if (discord == null) return null

            val channelOptional = discord.getChannelById(id)
            return if (channelOptional.isEmpty) null
            else if (channelOptional.get().type != ChannelType.SERVER_TEXT_CHANNEL) null
            else channelOptional.get().asServerTextChannel().get()
        }

        val discord: DiscordApi? = if (config.enabled) DiscordApiBuilder().setToken(config.token).login().join() else null

        val discordPrefix = "${ChatColor.PURPLE}[DISCORD]"

        val discordChannel: ServerTextChannel? = getDiscordChannel(config.channel)
    }
}
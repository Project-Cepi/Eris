package world.cepi.chatextension

import com.google.gson.Gson
import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.channel.ChannelType
import org.javacord.api.entity.channel.ServerTextChannel
import org.javacord.api.entity.server.invite.Invite
import org.javacord.api.entity.server.invite.InviteBuilder
import world.cepi.chatextension.discord.*
import world.cepi.chatextension.events.styleFormattedChat
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
            player.addEventCallback(PlayerChatEvent::class.java) {event ->
                chatToDiscord(event)
                styleFormattedChat(event)
            }
            onJoin(player)
            player.addEventCallback(PlayerDisconnectEvent::class.java) {event -> onLeave(event.player)}
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
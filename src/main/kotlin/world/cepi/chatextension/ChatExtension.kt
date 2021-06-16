package world.cepi.chatextension

import com.google.gson.Gson
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import net.minestom.server.MinecraftServer
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.EventFilter
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.extensions.Extension;
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
import org.javacord.api.entity.channel.ChannelType
import org.javacord.api.entity.channel.ServerTextChannel
import world.cepi.chatextension.debug.ErisCommand
import world.cepi.chatextension.discord.*
import world.cepi.chatextension.emojis.EmojiCommand
import world.cepi.chatextension.events.styleFormattedChat
import world.cepi.chatextension.tab.TabHandler
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.kstom.event.listenOnly
import java.io.File

class ChatExtension : Extension() {

    override fun initialize() {

        val connectionManager = MinecraftServer.getConnectionManager()

        DiscordLink.register()
        YoutubeLink.register()
        WebsiteLink.register()
        EmojiCommand.register()
        ErisCommand.register()

        val playerNode = EventNode.type("eris-player", EventFilter.PLAYER)

        connectionManager.addPlayerInitialization { player ->
            onJoin(player)
        }

        playerNode.listenOnly<PlayerSpawnEvent> {
            Audiences.all().sendMessage(
                Component.text("JOIN", NamedTextColor.GREEN, TextDecoration.BOLD)
                    .append(Component.space())
                    .append(Component.text("|", NamedTextColor.DARK_GRAY).decoration(TextDecoration.BOLD, false))
                    .append(Component.space())
                    .append(Component.text(player.username, NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))
            )
            TabHandler.loadTab(player)
            player.showTitle(Title.title(Component.empty(), Component.empty()))
        }

        playerNode.listenOnly<PlayerChatEvent> {
            chatToDiscord(this)
            styleFormattedChat(this)
        }

        playerNode.listenOnly<PlayerDisconnectEvent> {
            Audiences.all().sendMessage(
                Component.text("LEAVE", NamedTextColor.RED, TextDecoration.BOLD)
                    .append(Component.space())
                    .append(Component.text("|", NamedTextColor.DARK_GRAY).decoration(TextDecoration.BOLD, false))
                    .append(Component.space())
                    .append(Component.text(player.username, NamedTextColor.GRAY).decoration(TextDecoration.BOLD, false))
            )
            onLeave(this.player)
        }

        eventNode.addChild(playerNode)

        if (discord != null) {
            discord.addMessageCreateListener(DiscordToChat)
            discord.addServerMemberJoinListener(OnJoin)
        }

        logger.info("[ChatExtension] has been enabled!")

        if (config.enabled) {
            logger.info("[ChatExtension] Your discord bot can be invited with this link: ${discord?.createBotInvite()}")
        }
    }

    override fun terminate() {

        DiscordLink.unregister()
        YoutubeLink.unregister()
        WebsiteLink.unregister()
        EmojiCommand.unregister()
        ErisCommand.unregister()

        logger.info("[ChatExtension] has been disabled!")
    }

    companion object {
        val config: DiscordConfig by lazy {
            val configFile = File("./discord-config.json")
            val gson = Gson()

            return@lazy if (!configFile.exists()) {
                configFile.writeText(gson.toJson(DiscordConfig()))
                DiscordConfig()
            } else gson.fromJson(configFile.reader(), DiscordConfig::class.java)
        }

        val discord: DiscordApi? = if (config.enabled)
            DiscordApiBuilder()
                .setToken(config.token)
                .setWaitForServersOnStartup(false)
                .login().join()
        else null

        val discordPrefix = Component.text("Discord: ", NamedTextColor.DARK_AQUA)
    }
}
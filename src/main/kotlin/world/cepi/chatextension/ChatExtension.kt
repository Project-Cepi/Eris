package world.cepi.chatextension

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import net.minestom.server.advancements.FrameType
import net.minestom.server.advancements.notifications.Notification
import net.minestom.server.advancements.notifications.NotificationCenter
import net.minestom.server.adventure.audience.Audiences
import net.minestom.server.event.EventFilter
import net.minestom.server.event.EventNode
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.extensions.Extension;
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import org.javacord.api.DiscordApi
import org.javacord.api.DiscordApiBuilder
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

        DiscordLink.register()
        YoutubeLink.register()
        WebsiteLink.register()
        EmojiCommand.register()
        ErisCommand.register()

        val playerNode = EventNode.type("eris-player", EventFilter.PLAYER)

        playerNode.listenOnly<PlayerLoginEvent> {
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
            NotificationCenter.send(
                Notification(
                    Component.text("Welcome to Cepi!", NamedTextColor.GRAY),
                    FrameType.TASK,
                    ItemStack.of(Material.OAK_DOOR)
                ),
                player
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

        val json = Json {
            ignoreUnknownKeys = true
        }

        val config: DiscordConfig by lazy {
            val configFile = File("./discord-config.json")

            return@lazy if (!configFile.exists()) {
                configFile.writeText(json.encodeToString(DiscordConfig()))
                DiscordConfig()
            } else json.decodeFromString(configFile.readText())
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
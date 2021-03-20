package world.cepi.chatextension.events

import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor.*
import net.minestom.server.event.player.PlayerChatEvent
import world.cepi.chatextension.emojis.Emoji

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat { chatEvent ->

        var messageComponent = MiniMessage.markdown().parse(chatEvent.message)

        MinecraftServer.getConnectionManager().onlinePlayers.forEach {
            messageComponent = messageComponent.replaceText(
                TextReplacementConfig.builder()
                    .matchLiteral(it.username)
                    .replacement { _ ->
                        it.playSound(Sound.sound(net.minestom.server.sound.Sound.BLOCK_NOTE_BLOCK_PLING, Sound.Source.PLAYER, 1f, 2f))
                        Component.text(it.username, NamedTextColor.YELLOW)
                            .append(Component.text().color(NamedTextColor.WHITE))
                    }.build())
        }

        Emoji.emojis.forEach {
            messageComponent = messageComponent.replaceText(TextReplacementConfig.builder().matchLiteral(":${it.name}:").replacement(
                Component.text(it.value)
            ).build())
        }

        return@setChatFormat MiniMessage.get().parse(
                "<dark_gray>[<gray>${chatEvent.player.level}<dark_gray>] <white>${chatEvent.player.username}<dark_gray> >> <gray>"
            ).append(messageComponent)
    }
}
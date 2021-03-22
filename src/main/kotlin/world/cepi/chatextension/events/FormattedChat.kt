package world.cepi.chatextension.events

import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.markdown.DiscordFlavor
import net.kyori.adventure.text.minimessage.transformation.TransformationType
import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.sound.SoundEvent
import world.cepi.chatextension.emojis.Emoji

val miniMessageFormat = MiniMessage.builder()
    .removeDefaultTransformations()
    .transformation(TransformationType.DECORATION)
    .markdown()
    .markdownFlavor(DiscordFlavor.get())
    .build()

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat { chatEvent ->

        var messageComponent = miniMessageFormat.parse(chatEvent.message)

        MinecraftServer.getConnectionManager().onlinePlayers.forEach {
            messageComponent = messageComponent.replaceText(
                TextReplacementConfig.builder()
                    .matchLiteral(it.username)
                    .replacement { _ ->
                        it.playSound(Sound.sound(SoundEvent.BLOCK_NOTE_BLOCK_PLING, Sound.Source.PLAYER, 1f, 2f))
                        Component.text(it.username, NamedTextColor.YELLOW)
                            .append(Component.text().color(NamedTextColor.WHITE))
                    }.build())
        }

        Emoji.emojis.forEach {
            messageComponent = messageComponent.replaceText(TextReplacementConfig.builder().matchLiteral(":${it.name}:").replacement(
                Component.text(it.value)
            ).build())
        }

        return@setChatFormat Component.text("[", NamedTextColor.DARK_GRAY)
            .append(Component.text(chatEvent.player.level, NamedTextColor.GRAY))
            .append(Component.text("]", NamedTextColor.DARK_GRAY))
            .append(Component.space())
            .append(Component.text(chatEvent.player.username, NamedTextColor.WHITE))
            .append(Component.space())
            .append(Component.text(">>", NamedTextColor.GRAY))
            .append(Component.space())
            .append(messageComponent.color(NamedTextColor.WHITE))
    }
}
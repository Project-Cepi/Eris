package world.cepi.chatextension.events

import de.themoep.minedown.adventure.MineDown
import de.themoep.minedown.adventure.MineDownParser
import net.kyori.adventure.platform.minestom.MinestomComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextColor.color
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ChatColor.*
import net.minestom.server.chat.RichMessage
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.sound.Sound
import net.minestom.server.sound.SoundCategory
import world.cepi.chatextension.emojis.Emoji
import world.cepi.kstom.asRich
import world.cepi.kstom.plus
import kotlin.reflect.jvm.internal.impl.descriptors.Named

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat { chatEvent ->

        val mineDown = MineDown(chatEvent.message)
        mineDown.disable(MineDownParser.Option.ADVANCED_FORMATTING)
        var messageComponent = mineDown.toComponent()

        MinecraftServer.getConnectionManager().onlinePlayers.forEach {
            messageComponent = messageComponent.replaceText(
                TextReplacementConfig.builder()
                    .matchLiteral(it.username)
                    .replacement { _ ->
                        it.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.PLAYERS, 1f, 2f)
                        Component.text(it.username, NamedTextColor.YELLOW)
                            .append(Component.text().color(NamedTextColor.WHITE))
                    }.build())
        }

        Emoji.emojis.forEach {
            messageComponent = messageComponent.replaceText(TextReplacementConfig.builder().matchLiteral(":${it.name}:").replacement(
                Component.text(it.value)
            ).build())
        }

        return@setChatFormat MinestomComponentSerializer.get().serialize(
            MiniMessage.get().parse(
                "<dark_gray>[<gray>${chatEvent.player.level}<dark_gray>] <white>${chatEvent.player.username}<dark_gray> >> <gray>"
            ).append(messageComponent)
        )
    }
}
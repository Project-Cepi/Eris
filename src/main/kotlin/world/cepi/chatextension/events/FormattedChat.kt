package world.cepi.chatextension.events

import de.themoep.minedown.adventure.MineDown
import de.themoep.minedown.adventure.MineDownParser
import net.kyori.adventure.platform.minestom.MinestomComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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

        var message = chatEvent.message

        Emoji.emojis.forEach { message = message.replace(":${it.name}:", it.value) }

        MinecraftServer.getConnectionManager().onlinePlayers.forEach {
            if (message.contains(it.username)) {
                message = message.replace(it.username, YELLOW + it.username + WHITE)
                it.playSound(Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.PLAYERS, 1f, 2f)
            }
        }

        val mineDown = MineDown(message)
        mineDown.disable(MineDownParser.Option.LEGACY_COLORS)
        mineDown.disable(MineDownParser.Option.ADVANCED_FORMATTING)

        return@setChatFormat MinestomComponentSerializer.get().serialize(
            MiniMessage.get().parse(
                "<dark_gray>[<gray>${chatEvent.player.level}<dark_gray>] <white>${chatEvent.player.username}<dark_gray> >> <gray>"
            ).append(mineDown.toComponent())
        )
    }
}
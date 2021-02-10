package world.cepi.chatextension.events

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ChatColor.*
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.sound.Sound
import net.minestom.server.sound.SoundCategory
import world.cepi.chatextension.emojis.Emoji
import world.cepi.kstom.asRich
import world.cepi.kstom.plus

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

        return@setChatFormat "$DARK_GRAY[$GRAY${chatEvent.player.level}$DARK_GRAY] $WHITE${chatEvent.player.displayName ?: chatEvent.player.username} $DARK_GRAY>> $WHITE$message".asRich()
    }
}
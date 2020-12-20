package world.cepi.chatextension.events

import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.RichMessage
import net.minestom.server.event.player.PlayerChatEvent

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat {
        RichMessage.of(ColoredText.of("${it.player.displayName ?: it.player.username} ${ChatColor.DARK_GRAY}>> ${ChatColor.WHITE}${it.message}"))
    }
}
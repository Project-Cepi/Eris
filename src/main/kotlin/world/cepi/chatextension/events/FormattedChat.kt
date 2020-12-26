package world.cepi.chatextension.events

import net.minestom.server.chat.ChatColor
import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.RichMessage
import net.minestom.server.event.player.PlayerChatEvent
import world.cepi.chatextension.emojis.Emoji

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat { chatEvent ->

        var message = chatEvent.message
        Emoji.emojis.forEach { message = message.replace(":${it.name}:", it.value) }

        RichMessage.of(ColoredText.of("${chatEvent.player.displayName ?: chatEvent.player.username} ${ChatColor.DARK_GRAY}>> ${ChatColor.WHITE}${message}"))
    }
}
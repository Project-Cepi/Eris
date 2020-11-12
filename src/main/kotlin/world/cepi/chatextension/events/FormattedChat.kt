package world.cepi.chatextension.events

import net.minestom.server.chat.ColoredText
import net.minestom.server.chat.RichMessage
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerChatEvent

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat {
        RichMessage.of(ColoredText.of("${it.sender.displayName} >> ${it.message}"))
    }
}
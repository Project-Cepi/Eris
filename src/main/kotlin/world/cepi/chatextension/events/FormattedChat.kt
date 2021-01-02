package world.cepi.chatextension.events

import net.minestom.server.chat.ChatColor.*
import net.minestom.server.event.player.PlayerChatEvent
import world.cepi.chatextension.emojis.Emoji
import world.cepi.kstom.asRich

fun styleFormattedChat(event: PlayerChatEvent) {
    event.setChatFormat { chatEvent ->

        var message = chatEvent.message
        Emoji.emojis.forEach { message = message.replace(":${it.name}:", it.value) }

        "$DARK_GRAY[$GRAY${chatEvent.player.level}$DARK_GRAY] $WHITE${chatEvent.player.displayName ?: chatEvent.player.username} $DARK_GRAY>> $WHITE$message".asRich()
    }
}
package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.*
import org.javacord.api.event.message.MessageCreateEvent
import world.cepi.chatextension.ChatExtension

fun discordToChat(event: MessageCreateEvent) {
    val config = ChatExtension.config
    if (event.channel.id.toInt() != config.channel) return
    if (event.messageAuthor.isYourself) return

    for (player in MinecraftServer.getConnectionManager().onlinePlayers) {
        MinecraftServer.getConnectionManager().broadcastMessage(RichMessage()
                .append(ColoredText.of("${ChatColor.BOLD}[${ChatColor.PURPLE}DISCORD${ChatColor.WHITE}] ${ChatColor.NO_COLOR}<${event.messageAuthor.displayName}> ${event.messageContent}"))
                .setClickEvent(ChatClickEvent.openUrl(event.messageLink.ref))
                .setHoverEvent(ChatHoverEvent.showText(ColoredText.of(ChatColor.PURPLE, "Message from the Cepi discord! Come join us at [discord link]")))
        )
    }
}
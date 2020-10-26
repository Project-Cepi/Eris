package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.*
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import world.cepi.chatextension.ChatExtension

class DiscordToChat : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val config = ChatExtension.config
        if (event.channel.id != config.channel) return
        if (event.messageAuthor.isYourself) return

        for (player in MinecraftServer.getConnectionManager().onlinePlayers) {
            MinecraftServer.getConnectionManager().broadcastMessage(RichMessage
                    .of(ColoredText.of("${ChatColor.BOLD}[${ChatColor.PURPLE}DISCORD${ChatColor.WHITE}] ${ChatColor.NO_COLOR}<${event.messageAuthor.displayName}> ${event.messageContent}"))
                    .setClickEvent(ChatClickEvent.openUrl(event.messageLink.ref))
                    .setHoverEvent(ChatHoverEvent.showText(ColoredText.of(ChatColor.PURPLE, "Message from the Cepi discord! Come join us at [discord link]")))
            )
        }
    }
}


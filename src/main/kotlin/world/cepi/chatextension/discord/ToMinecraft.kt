package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.chat.*
import org.javacord.api.entity.server.invite.InviteBuilder
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.listener.message.MessageCreateListener
import world.cepi.chatextension.ChatExtension

class DiscordToChat : MessageCreateListener {
    var inviteLink: String? = null

    override fun onMessageCreate(event: MessageCreateEvent) {
        val config = ChatExtension.config
        if (event.channel.id != config.channel) return
        if (event.messageAuthor.isYourself) return

        if (inviteLink == null) event.serverTextChannel.ifPresent {
            val invite = InviteBuilder(event.serverTextChannel.get())
                    .setAuditLogReason("Automatic invite link created for a chat message")
                    .setNeverExpire()
                    .setTemporary(true)
                    .create()
            inviteLink = invite.get().url.toString()
        }

        MinecraftServer.getConnectionManager().broadcastMessage(RichMessage
                .of(ColoredText.of("${ChatColor.BOLD}[${ChatColor.PURPLE}DISCORD${ChatColor.WHITE}] ${ChatColor.NO_COLOR}<${event.messageAuthor.displayName}> ${event.messageContent}"))
                .setClickEvent(ChatClickEvent.openUrl(inviteLink ?: ""))
                .setHoverEvent(ChatHoverEvent.showText(ColoredText.of(ChatColor.PURPLE, "Message from the Cepi discord! Come join us at [discord link]"))))
    }
}


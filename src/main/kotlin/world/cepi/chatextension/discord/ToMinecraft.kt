package world.cepi.chatextension.discord

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.adventure.audience.Audiences
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.event.server.member.ServerMemberJoinEvent
import org.javacord.api.listener.message.MessageCreateListener
import org.javacord.api.listener.server.member.ServerMemberJoinListener
import world.cepi.chatextension.ChatExtension

object DiscordToChat : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val config = ChatExtension.config
        if (event.channel.id != config.channel) return
        if (event.messageAuthor.isYourself) return
        Audiences.players().sendMessage(
            ChatExtension.discordPrefix
                .append(Component.text( event.messageAuthor.displayName, NamedTextColor.WHITE))
                .append(Component.text(" >> ", NamedTextColor.GRAY))
                .append(Component.text(event.messageContent, NamedTextColor.WHITE)
                    .clickEvent(ClickEvent.openUrl(config.inviteLink))
                    .hoverEvent(
                        HoverEvent.showText(Component.text("Message from the Cepi discord! Come join us at ${config.inviteLink}", NamedTextColor.LIGHT_PURPLE))
                    )
            )
        )
    }
}

object OnJoin : ServerMemberJoinListener {
    override fun onServerMemberJoin(event: ServerMemberJoinEvent) {
        val config = ChatExtension.config
        Audiences.players().sendMessage(
            Component.text("${ChatExtension.discordPrefix} ${event.user.name} has joined us on Discord!", NamedTextColor.GREEN)
                .clickEvent(ClickEvent.openUrl(config.inviteLink))
                .hoverEvent(HoverEvent.showText(Component.text("Click to join us on Discord!", NamedTextColor.LIGHT_PURPLE)))
        )
    }
}
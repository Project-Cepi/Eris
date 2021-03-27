package world.cepi.chatextension.discord

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import org.javacord.api.event.message.MessageCreateEvent
import org.javacord.api.event.server.member.ServerMemberJoinEvent
import org.javacord.api.listener.message.MessageCreateListener
import org.javacord.api.listener.server.member.ServerMemberJoinListener
import world.cepi.chatextension.ChatExtension

class DiscordToChat : MessageCreateListener {
    override fun onMessageCreate(event: MessageCreateEvent) {
        val config = ChatExtension.config
        if (event.channel.id != config.channel) return
        if (event.messageAuthor.isYourself) return
        MinecraftServer.getConnectionManager().sendMessage(
            Component.text("${ChatExtension.discordPrefix} <${event.messageAuthor.displayName}> ${event.messageContent}")
                .clickEvent(ClickEvent.openUrl(event.messageLink.ref))
                .hoverEvent(HoverEvent.showText(Component.text("Message from the Cepi discord! Come join us at [discord link]", NamedTextColor.LIGHT_PURPLE)))
        )
    }
}

class OnJoin : ServerMemberJoinListener {
    override fun onServerMemberJoin(event: ServerMemberJoinEvent) {
        val config = ChatExtension.config
        MinecraftServer.getConnectionManager().sendMessage(
            Component.text("${ChatExtension.discordPrefix} ${event.user.name} has joined us on Discord!", NamedTextColor.GREEN)
                .clickEvent(ClickEvent.openUrl(config.inviteLink))
                .hoverEvent(HoverEvent.showText(Component.text("Click to join us on Discord!", NamedTextColor.LIGHT_PURPLE)))
        )
    }
}
package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerChatEvent
import org.javacord.api.entity.channel.Channel
import org.javacord.api.entity.channel.ChannelType
import org.javacord.api.entity.channel.TextChannel
import org.javacord.api.entity.message.MessageAuthor
import org.javacord.api.entity.message.embed.Embed
import org.javacord.api.entity.message.embed.EmbedBuilder
import world.cepi.chatextension.ChatExtension
import java.awt.Color
import java.io.File

fun grabChannel(): TextChannel? {
    val channelOptional = ChatExtension.discord?.getChannelById(ChatExtension.config.channel) ?: return null

    if (!channelOptional.isPresent || channelOptional.get().type != ChannelType.SERVER_TEXT_CHANNEL) {
        MinecraftServer.LOGGER.info("Error! Configured channel ${ChatExtension.config.channel} is invalid!")
        return null
    }

    return channelOptional.get().asTextChannel().get()
}

fun chatToDiscord(event: PlayerChatEvent) {
    val channel = grabChannel() ?: return

    channel.sendMessage("**<${event.player.displayName ?: event.player.username}>** ${event.message}")
}

private fun joinLeaveEmbed(player: Player, type: EmbedType): EmbedBuilder = EmbedBuilder()
            .setColor(if (type == EmbedType.JOIN) Color.green else Color.RED)
            .setAuthor(
                "${player.username} has ${
                    if (type == EmbedType.JOIN) "joined"
                    else "left"
                } the server",
                "",
                "https://minotar.net/helm/${player.uuid.toString().replace("-", "")}/128.png"
            )

fun onJoin(player: Player) {
    if (ChatExtension.discord == null) return
    val embed = joinLeaveEmbed(player, EmbedType.JOIN)

    val channel = grabChannel() ?: return

    channel.asServerTextChannel().get().sendMessage(embed)
}

fun onLeave(player: Player) {
    if (ChatExtension.discord == null) return

    val embed = joinLeaveEmbed(player, EmbedType.LEAVE)
    val channelOptional = ChatExtension.discord.getChannelById(ChatExtension.config.channel)

    if (!channelOptional.isPresent || channelOptional.get().type != ChannelType.SERVER_TEXT_CHANNEL) {
        MinecraftServer.LOGGER.info("Error! Configured channel ${ChatExtension.config.channel} is invalid!")
        return
    }

    val channel = channelOptional.get()

    channel.asServerTextChannel().get().sendMessage(embed)
}

private enum class EmbedType {
    LEAVE,
    JOIN
}
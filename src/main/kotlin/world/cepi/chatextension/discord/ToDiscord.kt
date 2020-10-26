package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerChatEvent
import org.javacord.api.entity.channel.ChannelType
import org.javacord.api.entity.message.Message
import org.javacord.api.util.logging.ExceptionLogger
import world.cepi.chatextension.ChatExtension

fun chatToDiscord(event: PlayerChatEvent) {
    val config = ChatExtension.config
    val discord = ChatExtension.discord ?: return
    val channelOptional = discord.getChannelById(config.channel)
    if (!channelOptional.isPresent) {
        MinecraftServer.getLOGGER().info("ERROR! Channel ${config.channel} does not exist")
        return
    } else if (channelOptional.get().type != ChannelType.SERVER_TEXT_CHANNEL) {
        MinecraftServer.getLOGGER().info("Channel type invalid")
        return
    }
    val channel = channelOptional.get().asTextChannel().get()
    channel.sendMessage("**<${event.sender.displayName}>** ${event.message}")
            .exceptionally(ExceptionLogger.get())
}
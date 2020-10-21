package world.cepi.chatextension.discord

import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.PlayerChatEvent
import org.javacord.api.entity.channel.ChannelType
import world.cepi.chatextension.ChatExtension

fun chatToDiscord(event: PlayerChatEvent) {
    val config = ChatExtension.config
    val discord = ChatExtension.discord ?: return
    val channelOptional = discord.getChannelById(config.channel.toLong())
    if (channelOptional.isPresent || channelOptional.get().type != ChannelType.SERVER_TEXT_CHANNEL) {
        MinecraftServer.getLOGGER().info("ERROR! Channel ${config.channel} is not a valid channel!")
        return
    }
    val channel = channelOptional.get().asTextChannel().get()
    channel.sendMessage("**<${event.sender.displayName}>** ${event.message}")
}
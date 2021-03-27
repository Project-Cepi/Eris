package world.cepi.chatextension

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.command.CommandSender
import world.cepi.kstom.command.SimpleKommand

const val discordLink = "https://discord.cepi.world"
const val websiteLink = "https://cepi.world"
const val youtubeLink = "https://youtube.cepi.world"

fun generateInvite(sender: CommandSender, link: String, name: String, color: NamedTextColor) {
    sender.sendMessage(
        Component.text("$name:", color)
            .append(Component.space())
            .append(
                Component.text("Click Here! ($link)", NamedTextColor.GRAY, TextDecoration.ITALIC, TextDecoration.UNDERLINED)
                    .clickEvent(ClickEvent.openUrl(link))
                    .hoverEvent(Component.text("Click here!", NamedTextColor.GRAY))
            )
    )
}

object DiscordLink : SimpleKommand("discord", process = { sender, _, _ ->
    generateInvite(sender, discordLink, "Discord", NamedTextColor.BLUE)
    true
})

object WebsiteLink : SimpleKommand("website", process = { sender, _, _ ->
    generateInvite(sender, websiteLink, "Website", NamedTextColor.GREEN)
    true
})

object YoutubeLink : SimpleKommand("youtube", process = { sender, _, _ ->
    generateInvite(sender, youtubeLink, "Youtube", NamedTextColor.RED)
    true
})
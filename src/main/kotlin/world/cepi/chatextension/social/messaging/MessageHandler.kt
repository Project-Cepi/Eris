package world.cepi.chatextension.social.messaging

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import world.cepi.chatextension.events.miniMessageFormat

object MessageHandler {

    fun send(from: CommandSender, to: Player, message: String) {

        val fromUsername = (from as? Player)?.username ?: "Console"

        from.sendMessage(
            Component.text("To ", TextColor.color(209, 209, 209))
                .append(Component.text(to.username, NamedTextColor.GRAY))
                .append(Component.text(" // ", NamedTextColor.DARK_GRAY))
                .append(miniMessageFormat.parse(message))
        )

        to.sendMessage(
            Component.text("From ", TextColor.color(209, 209, 209))
                .append(Component.text(fromUsername, NamedTextColor.GRAY))
                .append(Component.text(" // ", NamedTextColor.DARK_GRAY))
                .append(miniMessageFormat.parse(message))
        )
    }

}
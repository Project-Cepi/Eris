package world.cepi.chatextension.social.messaging

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.minestom.server.command.CommandSender
import net.minestom.server.entity.Player
import org.fusesource.jansi.Ansi.ansi
import org.slf4j.LoggerFactory
import world.cepi.chatextension.events.FormattedChat

/**
 * Handles messaging between two users, including console.
 */
object MessageHandler {

    val logger = LoggerFactory.getLogger("DirectMessage")

    /**
     * Sends a message from [from] to [to] with the [message] payload
     *
     * @param from The sender of this message
     * @param to Who to send this message to
     * @param message The message to send (markdown support)
     */
    fun send(from: CommandSender, to: Player, message: String) {

        val fromUsername = (from as? Player)?.username ?: "Console"

        from.sendMessage(
            Component.text("To ", TextColor.color(209, 209, 209))
                .append(Component.text(to.username, NamedTextColor.GRAY))
                .append(Component.text(" // ", NamedTextColor.DARK_GRAY))
                .append(FormattedChat.miniMessageFormat.parse(message))
        )

        to.sendMessage(
            Component.text("From ", TextColor.color(209, 209, 209))
                .append(Component.text(fromUsername, NamedTextColor.GRAY))
                .append(Component.text(" // ", NamedTextColor.DARK_GRAY))
                .append(FormattedChat.miniMessageFormat.parse(message))
        )

        logger.info(
            ansi()
                .fgDefault().a(fromUsername)
                .fgRgb(156, 156, 156).a(" -> ")
                .fgDefault().a(to.username)
                .fgRgb(156, 156, 156).a(" // ")
                .fgDefault().a(LegacyComponentSerializer.legacyAmpersand().serialize(FormattedChat.miniMessageFormat.parse(message)))
                .reset().toString()
        )
    }

}
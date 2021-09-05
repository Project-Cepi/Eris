package world.cepi.chatextension.events

import net.minestom.server.event.player.PlayerCommandEvent
import org.fusesource.jansi.Ansi.ansi
import org.slf4j.LoggerFactory

object CommandLogger {

    val logger = LoggerFactory.getLogger("Command")

    fun hook(event: PlayerCommandEvent) = with(event) {

        logger.info(
            ansi()
                .fgDefault().a(player.username)
                .fgRgb(156, 156, 156).a(" // ")
                .fgDefault().a(command)
                .toString()
        )

    }

}
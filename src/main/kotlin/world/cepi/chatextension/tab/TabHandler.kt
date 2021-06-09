package world.cepi.chatextension.tab

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import world.cepi.kstom.adventure.asMini

/** Handle tab formats */
object TabHandler {
    val header = "<gradient:green:#00b9ff>cepi.world</gradient>".asMini()
    val footer = "  <gradient:gray:white>Open Alpha Development</gradient>  ".asMini()

    fun loadTab(player: Player) {

        player.sendPlayerListHeaderAndFooter(
            Component.newline().append(header).append(Component.newline()),
            Component.newline().append(footer).append(Component.newline())
        )
    }

}
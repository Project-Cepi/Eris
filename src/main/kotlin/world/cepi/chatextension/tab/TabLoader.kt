package world.cepi.chatextension.tab

import net.kyori.adventure.text.Component
import net.minestom.server.entity.Player
import world.cepi.chatextension.tab.TabObjects.footer
import world.cepi.chatextension.tab.TabObjects.header
import world.cepi.kstom.adventure.asMini

/** Keep these expensive objects in memory! */
object TabObjects {
    val header = "<gradient:green:#00b9ff>cepi.world</gradient>".asMini()
    val footer = "  <gradient:gray:white>Open Alpha Development</gradient>  ".asMini()
}

fun loadTab(player: Player) {

    player.sendPlayerListHeaderAndFooter(
        Component.newline().append(header).append(Component.newline()),
        Component.newline().append(footer).append(Component.newline())
    )
}
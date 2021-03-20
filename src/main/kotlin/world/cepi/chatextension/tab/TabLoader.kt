package world.cepi.chatextension.tab

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.entity.Player
import world.cepi.chatextension.tab.TabObjects.footer
import world.cepi.chatextension.tab.TabObjects.header

/** Keep these expensive objects in memory! */
object TabObjects {
    val header = MiniMessage.get().parse("<gradient:green:#00b9ff>cepi.world</gradient>")
    val footer = MiniMessage.get().parse("  <gradient:gray:white>Open Alpha Development</gradient>  ")
}

fun loadTab(player: Player) {

    player.sendPlayerListHeaderAndFooter(
        Component.newline().append(header).append(Component.newline()),
        Component.newline().append(footer).append(Component.newline())
    )
}
package world.cepi.chatextension.tab

import net.kyori.adventure.platform.minestom.MinestomComponentSerializer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.minestom.server.entity.Player
import world.cepi.chatextension.tab.TabObjects.footer
import world.cepi.chatextension.tab.TabObjects.header

/** Keep these expensive objects in memory! */
object TabObjects {
    val header = MiniMessage.get().parse("<gradient:green:#00b9ff>cepi.world</gradient>")
    val footer = MiniMessage.get().parse("  <gradient:gray:white>Alpha Stage: Open Development</gradient>  ")
}

fun loadTab(player: Player) {

    player.sendHeaderFooter(
        Component.newline().append(header).append(Component.newline()).serialize(),
        Component.newline().append(footer).append(Component.newline()).serialize()
    )
}

fun Component.serialize() = MinestomComponentSerializer.get().serialize(this)
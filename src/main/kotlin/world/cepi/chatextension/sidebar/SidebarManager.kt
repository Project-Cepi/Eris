package world.cepi.chatextension.sidebar

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.minestom.server.entity.Player
import net.minestom.server.event.Event
import net.minestom.server.event.EventNode
import net.minestom.server.scoreboard.Sidebar
import world.cepi.chatextension.tab.TabHandler
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.sidebar.sidebar
import world.cepi.level.events.XPChangeEvent

object SidebarManager {

    fun emptyLine(index: Int) = Sidebar.ScoreboardLine(
        "emptyline${index}",
        Component.text(" "),
        index
    )

    fun loadSidebar(player: Player, node: EventNode<Event>) = sidebar(
        Component.text("Cepi").decoration(TextDecoration.BOLD, true)
    ) {

        fun level() = line("level", 2) {
            Component.text("Level: ", NamedTextColor.WHITE)
                .append(Component.text(player.level, NamedTextColor.GRAY))
        }

        add(
            level(),
            emptyLine(1),
            line("cepiDomain", 0, TabHandler.header.append(Component.text("    ")))
        )

        node.listenOnly<XPChangeEvent> {
            refresh(level())
        }

        addViewer(player)
    }


}
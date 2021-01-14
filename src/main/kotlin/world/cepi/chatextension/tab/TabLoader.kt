package world.cepi.chatextension.tab

import net.minestom.server.entity.Player
import world.cepi.kstom.asRich

fun loadTab(player: Player) {
    player.sendHeaderFooter("cepi.world\n".asRich(), "\n  Hey! Hows it doin?  ".asRich())
}
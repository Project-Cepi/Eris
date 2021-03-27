package world.cepi.chatextension.emojis

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command

object EmojiCommand : Command("emoji") {

    init {
        setDefaultExecutor { sender, _ ->
            Emoji.emojis.forEach {
                sender.sendMessage(
                    Component.text(it.name, NamedTextColor.GRAY)
                        .append(Component.text(":", NamedTextColor.DARK_GRAY))
                        .append(Component.space())
                        .append(Component.text(it.value, NamedTextColor.WHITE))
                )
            }
        }
    }

}
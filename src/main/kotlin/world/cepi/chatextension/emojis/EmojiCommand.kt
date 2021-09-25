package world.cepi.chatextension.emojis

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.Command
import world.cepi.kstom.command.kommand.Kommand

object EmojiCommand : Kommand({

    default {
        Emoji.emojis.forEach {
            sender.sendMessage(
                Component.text(":", NamedTextColor.WHITE)
                    .append(Component.text(it.name, NamedTextColor.GRAY))
                    .append(Component.text(":", NamedTextColor.WHITE))
                    .append(Component.text(" / ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(it.value, NamedTextColor.WHITE))
            )
        }
    }

}, "emoji")
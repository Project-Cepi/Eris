package world.cepi.chatextension.debug.subcommands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.command.kommand.Kommand
import java.lang.NumberFormatException

object UnicodeSubcommand : Kommand({

    val unicode = ArgumentType.String("unicodeString")

    syntax(unicode) {

        try {
            val result = Character.toChars(Integer.parseInt(context[unicode], 16)).joinToString("")

            sender.sendMessage(
                Component.text(result)
                    .hoverEvent(HoverEvent.showText(Component.text("Click to copy", NamedTextColor.YELLOW)))
                    .clickEvent(ClickEvent.copyToClipboard(result))
            )
        } catch (exception: NumberFormatException) {
            sender.sendMessage(Component.text("Invalid number!", NamedTextColor.RED))
        }
    }


}, "unicode")
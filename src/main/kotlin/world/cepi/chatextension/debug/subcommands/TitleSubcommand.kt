package world.cepi.chatextension.debug.subcommands

import net.kyori.adventure.title.Title
import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.arguments.MiniMessageArgument
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.kommand.Kommand

internal object TitleSubcommand : Kommand({
    val full by literal
    val clear by literal

    val title = MiniMessageArgument.single("title")

    val subtitle = MiniMessageArgument.single("subtitle")

    syntax(full, title, subtitle).onlyPlayers {
        player.showTitle(
            Title.title(
                context[title],
                context[subtitle]
            )
        )
    }

    syntax(clear).onlyPlayers {
        player.clearTitle()
    }
}, "title")
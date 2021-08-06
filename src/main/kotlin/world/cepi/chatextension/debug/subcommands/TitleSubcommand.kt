package world.cepi.chatextension.debug.subcommands

import net.kyori.adventure.title.Title
import net.minestom.server.command.builder.Command
import net.minestom.server.entity.Player
import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal

internal object TitleSubcommand : Command("title") {

    init {

        val full = "full".literal()

        val title = ErisCommand.miniMessage("title")

        val subtitle = ErisCommand.miniMessage("subtitle")

        addSyntax(full, title, subtitle) {
            (sender as? Player)
                ?.showTitle(
                    Title.title(
                        context[title],
                        context[subtitle]
                    )
                )
        }
    }

}
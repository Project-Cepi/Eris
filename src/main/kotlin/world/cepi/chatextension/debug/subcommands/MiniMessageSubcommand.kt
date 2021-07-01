package world.cepi.chatextension.debug.subcommands

import net.minestom.server.command.builder.Command
import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.addSyntax

internal object MiniMessageSubcommand : Command("mini") {

    init {
        addSyntax(ErisCommand.miniMessageVarargs) {
            sender.sendMessage(context[ErisCommand.miniMessageVarargs])
        }
    }

}
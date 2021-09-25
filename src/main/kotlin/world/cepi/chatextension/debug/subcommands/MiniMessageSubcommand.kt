package world.cepi.chatextension.debug.subcommands

import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.kommand.Kommand

internal object MiniMessageSubcommand : Kommand({

    syntax(ErisCommand.miniMessageVarargs) {
        sender.sendMessage(context[ErisCommand.miniMessageVarargs])
    }


}, "mini")
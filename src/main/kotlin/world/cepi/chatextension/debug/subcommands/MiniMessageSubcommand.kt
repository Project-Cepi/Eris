package world.cepi.chatextension.debug.subcommands

import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.arguments.MiniMessageArgument
import world.cepi.kstom.command.kommand.Kommand

internal object MiniMessageSubcommand : Kommand({

    syntax(MiniMessageArgument.vararg) {
        sender.sendMessage(!MiniMessageArgument.vararg)
    }


}, "mini")
package world.cepi.chatextension.debug

import world.cepi.chatextension.debug.subcommands.*
import world.cepi.kstom.command.kommand.Kommand

internal object ErisCommand : Kommand(name = "eris") {

    init {
        addSubcommands(
            ToastSubcommand,
            MiniMessageSubcommand,
            UnicodeSubcommand,
            TitleSubcommand
        )
    }

}
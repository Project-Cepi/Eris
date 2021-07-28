package world.cepi.chatextension.debug

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.chatextension.debug.subcommands.MiniMessageSubcommand
import world.cepi.chatextension.debug.subcommands.ToastSubcommand
import world.cepi.chatextension.debug.subcommands.UnicodeSubcommand
import world.cepi.kstom.adventure.asMini
import world.cepi.kstom.command.addSubcommands

internal object ErisCommand : Command("eris") {

    val miniMessageVarargs = ArgumentType.StringArray("message").map {
        it.joinToString(" ").asMini()
    }

    val miniMessage = ArgumentType.String("message").map {
        it.asMini()
    }

    init {
        addSubcommands(ToastSubcommand, MiniMessageSubcommand, UnicodeSubcommand)
    }

}
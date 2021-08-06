package world.cepi.chatextension.debug

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.chatextension.debug.subcommands.*
import world.cepi.kstom.adventure.asMini
import world.cepi.kstom.command.addSubcommands

internal object ErisCommand : Command("eris") {

    val miniMessageVarargs = ArgumentType.StringArray("message").map {
        it.joinToString(" ").asMini()
    }

    fun miniMessage(id: String) = ArgumentType.String(id).map {
        it.asMini()
    }

    init {
        addSubcommands(
            ToastSubcommand,
            MiniMessageSubcommand,
            UnicodeSubcommand,
            TitleSubcommand
        )
    }

}
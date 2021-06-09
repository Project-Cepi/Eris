package world.cepi.chatextension.debug

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.adventure.asMini
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal

object ErisCommand : Command("eris") {

    init {
        val minimessage = "mini".literal()
        val message = ArgumentType.StringArray("message").map {
            it.joinToString(" ")
        }

        addSyntax(minimessage, message) { sender, args ->
            sender.sendMessage(args[message].asMini())
        }
    }

}
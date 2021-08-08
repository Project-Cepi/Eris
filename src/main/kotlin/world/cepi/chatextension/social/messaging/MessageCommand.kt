package world.cepi.chatextension.social.messaging

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.Manager
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.ArgumentPlayer

object MessageCommand : Command("message", "msg", "tell") {

    init {
        val player = ArgumentPlayer("player").also {
            it.setCallback { sender, exception ->
                sender.sendMessage(exception.input)
                sender.sendMessage(Manager.connection.getPlayer(exception.input)?.username ?: "null")
            }
        }

        val message = ArgumentType.StringArray("message")
            .map { it.joinToString(" ") }

        addSyntax(player, message) {
            MessageHandler.send(sender, context[player], context[message])
        }
    }

}
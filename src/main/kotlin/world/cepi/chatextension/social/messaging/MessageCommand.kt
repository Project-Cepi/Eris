package world.cepi.chatextension.social.messaging

import net.minestom.server.command.builder.arguments.ArgumentType
import world.cepi.kstom.Manager
import world.cepi.kstom.command.arguments.ArgumentPlayer
import world.cepi.kstom.command.kommand.Kommand

object MessageCommand : Kommand({

    val player = ArgumentPlayer("player").also {
        it.setCallback { sender, exception ->
            sender.sendMessage(exception.input)
            sender.sendMessage(Manager.connection.getPlayer(exception.input)?.username ?: "null")
        }
    }

    val message = ArgumentType.StringArray("message")
        .map { it.joinToString(" ") }

    syntax(player, message) {
        MessageHandler.send(sender, context[player], context[message])
    }


}, "message", "msg", "tell")
package world.cepi.chatextension.emojis

import net.minestom.server.command.builder.Command

class EmojiCommand : Command("emoji") {

    init {
        setDefaultExecutor { sender, args ->
            Emoji.emojis.forEach {
                sender.sendMessage("${it.name}: ${it.value}")
            }
        }
    }

}
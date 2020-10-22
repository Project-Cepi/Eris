package world.cepi.chatextension

import net.minestom.server.extensions.Extension;

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ChatExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ChatExtension] has been disabled!")
    }

}
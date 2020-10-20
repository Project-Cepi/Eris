package world.cepi.chatextension

import net.minestom.server.extensions.Extension;

class ChatExtension : Extension() {

    override fun initialize() {
        logger.info("[ExampleExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[ExampleExtension] has been disabled!")
    }

}
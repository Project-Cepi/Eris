package world.cepi.chatextension

import net.minestom.server.chat.ChatClickEvent
import world.cepi.kstom.asRich
import world.cepi.kstom.command.KommandProcessor

class DiscordLink : KommandProcessor("discord", process = { sender, _, _ ->
    sender.sendMessage("Discord: https://discord.cepi.world".asRich().setClickEvent(ChatClickEvent.openUrl("https://discord.cepi.world")))
    true
})

class WebsiteLink : KommandProcessor("website", process = { sender, _, _ ->
    sender.sendMessage("Website: https://cepi.world".asRich().setClickEvent(ChatClickEvent.openUrl("https://cepi.world")))
    true
})

class YoutubeLink : KommandProcessor("youtube", process = { sender, _, _ ->
    sender.sendMessage("Youtube: https://youtube.cepi.world".asRich().setClickEvent(ChatClickEvent.openUrl("https://youtube.cepi.world")))
    true
})
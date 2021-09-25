package world.cepi.chatextension.debug.subcommands

import net.minestom.server.advancements.FrameType
import net.minestom.server.advancements.notifications.Notification
import net.minestom.server.advancements.notifications.NotificationCenter
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentEnum
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.item.ItemStack
import net.minestom.server.item.Material
import world.cepi.chatextension.debug.ErisCommand
import world.cepi.kstom.command.kommand.Kommand

internal object ToastSubcommand : Kommand({

    val toastType = ArgumentType.Enum("frameType", FrameType::class.java)
        .setFormat(ArgumentEnum.Format.LOWER_CASED)
        .setDefaultValue(FrameType.TASK)

    val item = ArgumentType.ItemStack("item")
        .setDefaultValue(ItemStack.of(Material.PAPER))

    val miniMessage = ErisCommand.miniMessage("miniMessage")

    syntax(miniMessage, toastType, item) {
        NotificationCenter.send(
            Notification(
                context[miniMessage],
                context[toastType],
                context[item]
            ),
            sender as Player
        )
    }

}, "toast")
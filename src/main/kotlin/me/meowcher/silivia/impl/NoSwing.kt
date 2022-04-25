package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import meteordevelopment.meteorclient.events.packets.PacketEvent.*
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket

class NoSwing : Melchior, Module(Casper.Reference.category, "no-swing", "Removes all hand Swings.")
{
    private val group = settings.defaultGroup
    private var noServerSwing = group.add(BoolSetting.Builder().name("no-server").description("Removes swings from server.").defaultValue(false).build())
    private var noForOthers = group.add(BoolSetting.Builder().name("remove-animations").description("Removes player swing animations.").defaultValue(false).build())

    @EventHandler private fun onPacketReceiveEvent(Event : Receive)
    {
        if (noServerSwing.get() && Event.packet is EntityAnimationS2CPacket)
        {
            Event.isCancelled = true
        }
    }

    @EventHandler private fun onPacketSendEvent(Event : Send)
    {
        if (noForOthers.get() && Event.packet is HandSwingC2SPacket)
        {
            Event.isCancelled = true
        }
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        player!!.handSwinging = false
        player!!.handSwingProgress = 0F
        player!!.lastHandSwingProgress = 0F
        player!!.handSwingTicks = 0
    }
}

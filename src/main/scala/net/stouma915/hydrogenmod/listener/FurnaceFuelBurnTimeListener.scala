package net.stouma915.hydrogenmod.listener

import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.item.BatteryItem

@Mod.EventBusSubscriber(modid = HydrogenMod.ModId)
object FurnaceFuelBurnTimeListener {

  @SubscribeEvent
  def onFurnaceFuelBurnTime(event: FurnaceFuelBurnTimeEvent): Unit =
    event.getItemStack.getItem match {
      case _: BatteryItem =>
        event.setBurnTime(200)
      case _ =>
    }

}

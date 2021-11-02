package net.stouma915.hydrogenmod.item

import net.minecraft.world.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.MODID,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object ItemRegister {
  @SubscribeEvent
  def registerItems(
      event: RegistryEvent.Register[Item]
  ): Unit =
    IndexedSeq(
      HydrogenItem.item,
      OxygenItem.item
    ).foreach(event.getRegistry.register)
}

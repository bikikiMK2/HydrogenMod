package net.stouma915.hydrogenmod.potion

import net.minecraft.world.item.alchemy.Potion
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.MODID,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object PotionRegister {
  @SubscribeEvent
  def registerPotion(event: RegistryEvent.Register[Potion]): Unit = {
    event.getRegistry.register(HydrogenWaterPotion.potion)
    event.getRegistry.register(OxygenWaterPotion.potion)
  }
}
package net.stouma915.hydrogenmod.potion.effect

import net.minecraft.world.effect.MobEffect
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object PotionEffectRegister {
  private val potionEffectsToRegister = List(
    HydrogenWaterEffect(),
    OxygenWaterEffect()
  )

  @SubscribeEvent
  def registerEffect(event: RegistryEvent.Register[MobEffect]): Unit =
    potionEffectsToRegister.foreach(event.getRegistry.register)
}

package net.stouma915.hydrogenmod.potion

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.potion.effect.OxygenWaterEffect

object OxygenWaterPotion {
  val potion: Potion =
    new Potion(
      new MobEffectInstance(OxygenWaterEffect.effect, 1200, 0, false, true)
    ).setRegistryName(HydrogenMod.MODID, "oxygen_water")
}

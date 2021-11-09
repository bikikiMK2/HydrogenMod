package net.stouma915.hydrogenmod.potion.effect

import net.minecraft.world.effect.{MobEffect, MobEffectCategory}
import net.stouma915.hydrogenmod.HydrogenMod

object OxygenWaterEffect {
  private val instance: MobEffect = new OxygenWaterEffect()
    .setRegistryName(HydrogenMod.MODID, "oxygen_water_effect")

  def apply(): MobEffect = instance
}

sealed class OxygenWaterEffect private ()
    extends MobEffect(MobEffectCategory.BENEFICIAL, -6684673)

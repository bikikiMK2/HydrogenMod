package net.stouma915.hydrogenmod.potion.effect

import net.minecraft.world.effect.{MobEffect, MobEffectCategory}
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenWaterEffect {

  private val instance: MobEffect = new HydrogenWaterEffect()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_water_effect")

  def apply(): MobEffect = instance

}

sealed class HydrogenWaterEffect private ()
    extends MobEffect(MobEffectCategory.HARMFUL, -6684673)

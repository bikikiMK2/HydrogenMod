package net.stouma915.hydrogenmod.potion.effect

import net.minecraft.world.effect.{MobEffect, MobEffectCategory}
import net.stouma915.hydrogenmod.HydrogenMod

object OxygenWaterEffect {
  var effect: MobEffect = new OxygenWaterEffect()
    .setRegistryName(HydrogenMod.MODID, "oxygen_water_effect")
}

class OxygenWaterEffect
    extends MobEffect(MobEffectCategory.BENEFICIAL, -6684673)

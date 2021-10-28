package net.stouma915.hydrogenmod.potion.effect

import net.minecraft.world.effect.{MobEffect, MobEffectCategory}
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenWaterEffect {
  var effect: MobEffect = new HydrogenWaterEffect()
    .setRegistryName(HydrogenMod.MODID, "hydrogen_water_effect")
}

class HydrogenWaterEffect
    extends MobEffect(MobEffectCategory.BENEFICIAL, -6684673)

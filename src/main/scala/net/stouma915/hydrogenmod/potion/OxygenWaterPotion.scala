package net.stouma915.hydrogenmod.potion

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.potion.effect.OxygenWaterEffect

object OxygenWaterPotion {
  val potion: Potion =
    new OxygenWaterPotion().setRegistryName(HydrogenMod.MODID, "oxygen_water")
}

class OxygenWaterPotion
    extends Potion(
      new MobEffectInstance(OxygenWaterEffect.effect, 1200, 0, false, true)
    )

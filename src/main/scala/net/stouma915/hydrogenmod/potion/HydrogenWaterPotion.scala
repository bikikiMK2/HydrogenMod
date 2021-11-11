package net.stouma915.hydrogenmod.potion

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.potion.effect.HydrogenWaterEffect

object HydrogenWaterPotion {
  private val instance: Potion = new HydrogenWaterPotion()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_water")

  def apply(): Potion = instance
}

sealed class HydrogenWaterPotion private ()
    extends Potion(
      new MobEffectInstance(HydrogenWaterEffect(), 1)
    )

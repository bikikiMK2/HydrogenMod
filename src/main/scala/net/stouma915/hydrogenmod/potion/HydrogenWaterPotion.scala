package net.stouma915.hydrogenmod.potion

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.potion.effect.HydrogenWaterEffect

object HydrogenWaterPotion {
  val potion: Potion = new HydrogenWaterPotion()
    .setRegistryName(HydrogenMod.MODID, "hydrogen_water")
}

class HydrogenWaterPotion
    extends Potion(
      new MobEffectInstance(HydrogenWaterEffect.effect, 1200, 0, false, true)
    )

package net.stouma915.hydrogenmod.potion

import net.minecraft.world.item.alchemy.Potion
import net.stouma915.hydrogenmod.HydrogenMod

object OxygenWaterPotion {
  val potion: Potion =
    new Potion().setRegistryName(HydrogenMod.MODID, "oxygen_water")
}

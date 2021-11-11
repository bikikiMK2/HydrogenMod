package net.stouma915.hydrogenmod.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object MetalRodItem {
  private val instance: Item =
    new MetalRodItem().setRegistryName(HydrogenMod.ModId, "metal_rod")

  def apply(): Item = instance
}

sealed class MetalRodItem private ()
    extends Item(new Properties().tab(HydrogenModTab()))

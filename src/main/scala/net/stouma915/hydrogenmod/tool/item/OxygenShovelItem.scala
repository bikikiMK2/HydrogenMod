package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{Item, ShovelItem}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object OxygenShovelItem {
  private val instance: Item =
    new OxygenShovelItem().setRegistryName(HydrogenMod.ModId, "oxygen_shovel")

  def apply(): Item = instance
}

sealed class OxygenShovelItem private ()
    extends ShovelItem(
      HydrogenTier(),
      1.0f,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

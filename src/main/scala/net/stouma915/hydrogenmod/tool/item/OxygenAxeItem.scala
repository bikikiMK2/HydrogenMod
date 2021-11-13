package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.{AxeItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.OxygenTier

object OxygenAxeItem {
  private val instance: Item =
    new OxygenAxeItem().setRegistryName(HydrogenMod.ModId, "oxygen_axe")

  def apply(): Item = instance
}

sealed class OxygenAxeItem private ()
    extends AxeItem(
      OxygenTier(),
      1.0f,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

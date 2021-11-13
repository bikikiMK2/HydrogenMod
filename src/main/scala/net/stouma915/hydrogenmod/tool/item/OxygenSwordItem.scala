package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{Item, SwordItem}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object OxygenSwordItem {
  private val instance: Item =
    new OxygenSwordItem().setRegistryName(HydrogenMod.ModId, "oxygen_sword")

  def apply(): Item = instance
}

sealed class OxygenSwordItem private ()
    extends SwordItem(
      HydrogenTier(),
      0,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

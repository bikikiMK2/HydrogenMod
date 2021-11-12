package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{Item, ShovelItem}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object HydrogenShovelItem {
  private val instance: Item = new HydrogenShovelItem()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_shovel")

  def apply(): Item = instance
}

sealed class HydrogenShovelItem private ()
    extends ShovelItem(
      HydrogenTier(),
      1.0f,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

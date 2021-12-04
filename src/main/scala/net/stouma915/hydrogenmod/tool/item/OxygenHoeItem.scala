package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{HoeItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.OxygenTier

object OxygenHoeItem {

  private val instance: Item =
    new OxygenHoeItem().setRegistryName(HydrogenMod.ModId, "oxygen_hoe")

  def apply(): Item = instance

}

sealed class OxygenHoeItem private ()
    extends HoeItem(
      OxygenTier(),
      1,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

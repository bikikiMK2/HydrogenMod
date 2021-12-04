package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{HoeItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object HydrogenHoeItem {

  private val instance: Item =
    new HydrogenHoeItem().setRegistryName(HydrogenMod.ModId, "hydrogen_hoe")

  def apply(): Item = instance

}

sealed class HydrogenHoeItem private ()
    extends HoeItem(
      HydrogenTier(),
      1,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

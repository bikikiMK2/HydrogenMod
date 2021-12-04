package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{AxeItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object HydrogenAxeItem {

  private val instance: Item =
    new HydrogenAxeItem().setRegistryName(HydrogenMod.ModId, "hydrogen_axe")

  def apply(): Item = instance

}

sealed class HydrogenAxeItem private ()
    extends AxeItem(
      HydrogenTier(),
      1.0f,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{Item, PickaxeItem}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.OxygenTier

object OxygenPickaxeItem {
  private val instance: Item =
    new OxygenPickaxeItem().setRegistryName(HydrogenMod.ModId, "oxygen_pickaxe")

  def apply(): Item = instance
}

sealed class OxygenPickaxeItem private ()
    extends PickaxeItem(
      OxygenTier(),
      1,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

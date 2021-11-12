package net.stouma915.hydrogenmod.tool.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{Item, PickaxeItem}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab
import net.stouma915.hydrogenmod.tool.tier.HydrogenTier

object HydrogenPickaxeItem {
  private val instance: Item = new HydrogenPickaxeItem()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_pickaxe")

  def apply(): Item = instance
}

sealed class HydrogenPickaxeItem private ()
    extends PickaxeItem(
      HydrogenTier(),
      1,
      1.0f,
      new Properties().tab(HydrogenModTab())
    )

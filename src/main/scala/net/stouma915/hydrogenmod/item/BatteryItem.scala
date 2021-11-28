package net.stouma915.hydrogenmod.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object BatteryItem {

  private val instance: Item =
    new BatteryItem().setRegistryName(HydrogenMod.ModId, "battery")

  def apply(): Item = instance

}

sealed class BatteryItem private ()
    extends Item(
      new Properties()
        .tab(HydrogenModTab())
        .durability(45)
        .setNoRepair()
    )

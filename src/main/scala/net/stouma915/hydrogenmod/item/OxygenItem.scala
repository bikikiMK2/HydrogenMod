package net.stouma915.hydrogenmod.item

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenItem {

  private val instance: Item =
    new OxygenItem().setRegistryName(HydrogenMod.ModId, "oxygen")

  def apply(): Item = instance

}

sealed class OxygenItem private ()
    extends Item(
      new Properties()
        .tab(HydrogenModTab())
        .food(
          new FoodProperties.Builder()
            .alwaysEat()
            .build()
        )
    )

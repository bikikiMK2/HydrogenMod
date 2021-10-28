package net.stouma915.hydrogenmod.item

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenItem {
  val item: Item = new OxygenItem().setRegistryName(HydrogenMod.MODID, "oxygen")
}

class OxygenItem
    extends Item(
      new Properties()
        .tab(HydrogenModTab.tab)
        .food(
          new FoodProperties.Builder()
            .alwaysEat()
            .build()
        )
    )

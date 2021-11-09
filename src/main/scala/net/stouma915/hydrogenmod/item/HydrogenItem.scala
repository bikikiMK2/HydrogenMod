package net.stouma915.hydrogenmod.item

import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Item
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object HydrogenItem {
  val instance: Item =
    new HydrogenItem().setRegistryName(HydrogenMod.MODID, "hydrogen")
}

sealed class HydrogenItem private ()
    extends Item(
      new Properties()
        .tab(HydrogenModTab.instance)
        .food(
          new FoodProperties.Builder()
            .alwaysEat()
            .build()
        )
    )

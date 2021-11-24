package net.stouma915.hydrogenmod.recipe.electrolysis

import net.minecraft.world.item.Items
import net.minecraft.world.item.ItemStack
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.{HydrogenItem, OxygenItem}
import net.stouma915.hydrogenmod.meta.recipe.electrolysis.ElectrolysisRecipe

sealed class ElectrolysisOfWater extends ElectrolysisRecipe {
  override def isCorrectAsInput(inputItem: ItemStack): Boolean =
    inputItem.getItem == Items.WATER_BUCKET

  override def getOutputItems(inputItem: ItemStack): List[ItemStack] =
    List(
      {
        val hydrogenItemStack = HydrogenItem().asItemStack
        hydrogenItemStack.setCount(2)
        hydrogenItemStack
      },
      OxygenItem().asItemStack
    )
}

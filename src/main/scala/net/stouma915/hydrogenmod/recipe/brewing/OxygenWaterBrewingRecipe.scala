package net.stouma915.hydrogenmod.recipe.brewing

import net.minecraft.world.item.{ItemStack, Items}
import net.minecraft.world.item.alchemy.{PotionUtils, Potions}
import net.minecraftforge.common.brewing.IBrewingRecipe
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.OxygenItem
import net.stouma915.hydrogenmod.potion.OxygenWaterPotion

sealed class OxygenWaterBrewingRecipe extends IBrewingRecipe {
  override def isInput(input: ItemStack): Boolean = {
    val inputItem = input.getItem
    (inputItem == Items.POTION || inputItem == Items.SPLASH_POTION || inputItem == Items.LINGERING_POTION) && PotionUtils
      .getPotion(input) == Potions.WATER
  }

  override def isIngredient(ingredient: ItemStack): Boolean =
    ingredient.sameItem(OxygenItem().asItemStack)

  override def getOutput(input: ItemStack, ingredient: ItemStack): ItemStack =
    if (isInput(input) && isIngredient(ingredient))
      PotionUtils.setPotion(
        new ItemStack(input.getItem),
        OxygenWaterPotion()
      )
    else
      ItemStack.EMPTY
}

package net.stouma915.hydrogenmod.meta.recipe.electrolysis

import net.minecraft.world.item.ItemStack

trait ElectrolysisRecipe {

  def isCorrectAsInput(inputItem: ItemStack): Boolean

  def leaveBucketOfInput: Boolean

  def getOutputItems(inputItem: ItemStack): List[ItemStack]

}

package net.stouma915.hydrogenmod.recipe.electrolysis

import net.stouma915.hydrogenmod.meta.recipe.electrolysis.ElectrolysisRecipe

object ElectrolysisRecipeRegistry {

  private var recipeList = List[ElectrolysisRecipe]()

  def register(recipe: ElectrolysisRecipe): Unit =
    recipeList = recipeList.appended(recipe)

  def registerAll(recipes: ElectrolysisRecipe*): Unit =
    recipeList = recipeList.appendedAll(recipes)

  def getAll: List[ElectrolysisRecipe] = recipeList

}

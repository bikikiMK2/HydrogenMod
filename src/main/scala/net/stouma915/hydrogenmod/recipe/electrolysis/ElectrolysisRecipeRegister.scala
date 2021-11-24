package net.stouma915.hydrogenmod.recipe.electrolysis

object ElectrolysisRecipeRegister {
  private val recipesToRegistry = List(
    ElectrolysisOfWater()
  )

  private[hydrogenmod] def register(): Unit =
    ElectrolysisRecipeRegistry.registerAll(recipesToRegistry: _*)
}

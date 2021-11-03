package net.stouma915.hydrogenmod

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.listener._
import net.stouma915.hydrogenmod.recipe.brewing._

object HydrogenMod {
  final val MODID = "hydrogenmod"
}

@Mod(HydrogenMod.MODID)
class HydrogenMod {
  Seq(
    new LivingEntityUseItemFinishListener,
    new LivingUpdateListener,
    new PlayerInteractListener
  ).foreach(MinecraftForge.EVENT_BUS.register)

  Seq(
    new HydrogenWaterBrewingRecipe,
    new OxygenWaterBrewingRecipe
  ).foreach(BrewingRecipeRegistry.addRecipe)
}

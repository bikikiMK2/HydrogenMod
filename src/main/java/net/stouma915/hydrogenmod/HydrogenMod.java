package net.stouma915.hydrogenmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.stouma915.hydrogenmod.listener.*;
import net.stouma915.hydrogenmod.recipe.brewing.HydrogenWaterBrewingRecipe;

import java.util.Arrays;
import java.util.Collections;

@Mod(HydrogenMod.MODID)
public class HydrogenMod {
    public static final String MODID = "hydrogenmod";

    public HydrogenMod() {
        Arrays.asList(
                new LivingEntityUseItemFinishListener(),
                new LivingUpdateListener(),
                new PlayerInteractListener()
        ).forEach(MinecraftForge.EVENT_BUS::register);

        Collections.singletonList(
                new HydrogenWaterBrewingRecipe()
        ).forEach(BrewingRecipeRegistry::addRecipe);
    }
}

package net.stouma915.hydrogenmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(HydrogenMod.MODID)
public class HydrogenMod {
    public static final String MODID = "hydrogenmod";

    public HydrogenMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}

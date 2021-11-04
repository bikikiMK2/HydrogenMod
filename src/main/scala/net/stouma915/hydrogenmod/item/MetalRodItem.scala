package net.stouma915.hydrogenmod.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object MetalRodItem {
  val item: Item =
    new MetalRodItem().setRegistryName(HydrogenMod.MODID, "metal_rod")
}

class MetalRodItem extends Item(new Properties().tab(HydrogenModTab.tab))

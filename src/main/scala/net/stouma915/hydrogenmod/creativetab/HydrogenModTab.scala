package net.stouma915.hydrogenmod.creativetab

import net.minecraft.world.item.{CreativeModeTab, ItemStack}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.item.HydrogenItem

object HydrogenModTab {
  val instance: CreativeModeTab = new HydrogenModTab
}

sealed class HydrogenModTab private ()
    extends CreativeModeTab(HydrogenMod.MODID) {
  override def makeIcon(): ItemStack = new ItemStack(HydrogenItem.instance)
}

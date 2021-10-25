package net.stouma915.hydrogenmod.creativetab

import net.minecraft.world.item.{CreativeModeTab, ItemStack}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.item.HydrogenItem

object HydrogenModTab {
  val tab: CreativeModeTab = new CreativeModeTab(HydrogenMod.MODID) {
    override def makeIcon(): ItemStack = new ItemStack(HydrogenItem.item)
  }
}

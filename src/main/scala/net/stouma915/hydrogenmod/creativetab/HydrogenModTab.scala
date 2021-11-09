package net.stouma915.hydrogenmod.creativetab

import net.minecraft.world.item.{CreativeModeTab, ItemStack}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.item.HydrogenItem

object HydrogenModTab {
  private val instance: CreativeModeTab = new HydrogenModTab

  def apply(): CreativeModeTab = instance
}

sealed class HydrogenModTab private ()
    extends CreativeModeTab(HydrogenMod.MODID) {
  override def makeIcon(): ItemStack = new ItemStack(HydrogenItem())
}

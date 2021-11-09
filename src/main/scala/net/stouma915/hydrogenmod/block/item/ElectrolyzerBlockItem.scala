package net.stouma915.hydrogenmod.block.item

import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object ElectrolyzerBlockItem {
  val instance: Item = new ElectrolyzerBlockItem()
    .setRegistryName(ElectrolyzerBlock.instance.getRegistryName)
}

sealed class ElectrolyzerBlockItem private ()
    extends BlockItem(
      ElectrolyzerBlock.instance,
      new Properties().tab(HydrogenModTab.instance)
    )

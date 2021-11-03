package net.stouma915.hydrogenmod.block.item

import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object ElectrolyzerBlockItem {
  val blockItem: Item = new ElectrolyzerBlockItem()
    .setRegistryName(ElectrolyzerBlock.block.getRegistryName)
}

class ElectrolyzerBlockItem
    extends BlockItem(
      ElectrolyzerBlock.block,
      new Properties().tab(HydrogenModTab.tab)
    )

package net.stouma915.hydrogenmod.block.item

import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{BlockItem, Item}
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object ElectrolyzerBlockItem {

  private val instance: Item = new ElectrolyzerBlockItem().setRegistryName(
    ElectrolyzerBlock().getRegistryName
  )

  def apply(): Item = instance

}

sealed class ElectrolyzerBlockItem private ()
    extends BlockItem(
      ElectrolyzerBlock(),
      new Properties().tab(HydrogenModTab())
    )

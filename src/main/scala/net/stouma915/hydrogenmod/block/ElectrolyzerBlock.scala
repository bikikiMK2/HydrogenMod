package net.stouma915.hydrogenmod.block

import net.minecraft.world.level.block.{Block, SoundType}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material
import net.stouma915.hydrogenmod.HydrogenMod

object ElectrolyzerBlock {
  val block: Block =
    new ElectrolyzerBlock().setRegistryName(HydrogenMod.MODID, "electrolyzer")
}

class ElectrolyzerBlock
    extends Block(
      Properties
        .of(Material.STONE)
        .sound(SoundType.GLASS)
        .noOcclusion()
        .destroyTime(0.45f)
    )

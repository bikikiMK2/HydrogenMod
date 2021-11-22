package net.stouma915.hydrogenmod.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.{Block, SoundType}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.{CollisionContext, VoxelShape}
import net.stouma915.hydrogenmod.HydrogenMod

object ElectrolyzerBlock {
  private val instance: Block =
    new ElectrolyzerBlock().setRegistryName(HydrogenMod.ModId, "electrolyzer")

  def apply(): Block = instance
}

sealed class ElectrolyzerBlock private ()
    extends Block(
      Properties
        .of(Material.STONE)
        .sound(SoundType.GLASS)
        .noOcclusion()
        .destroyTime(0.45f)
        .requiresCorrectToolForDrops()
        .lightLevel((_: BlockState) => 1)
    ) {
  override def getShape(
      p_51973_ : BlockState,
      p_51974_ : BlockGetter,
      p_51975_ : BlockPos,
      p_51976_ : CollisionContext
  ): VoxelShape =
    Block.box(0.0d, 0.0d, 0.0d, 16.0d, 32.0d, 16.0d)
}

package net.stouma915.hydrogenmod.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.{Block, SoundType}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.{
  BooleanOp,
  CollisionContext,
  Shapes,
  VoxelShape
}
import net.stouma915.hydrogenmod.HydrogenMod

object ElectrolyzerBlock {
  val instance: Block =
    new ElectrolyzerBlock().setRegistryName(HydrogenMod.MODID, "electrolyzer")
}

sealed class ElectrolyzerBlock private ()
    extends Block(
      Properties
        .of(Material.STONE)
        .sound(SoundType.GLASS)
        .noOcclusion()
        .destroyTime(0.45f)
        .requiresCorrectToolForDrops()
    ) {

  @SuppressWarnings(Array("deprecation"))
  override def getShape(
      p_51973_ : BlockState,
      p_51974_ : BlockGetter,
      p_51975_ : BlockPos,
      p_51976_ : CollisionContext
  ): VoxelShape =
    Shapes.join(
      Shapes.block(),
      Block.box(
        1.0d, 1.0d, 1.0d, 15.0d, 16.0d, 15.0d
      ),
      BooleanOp.ONLY_FIRST
    )
}

package net.stouma915.hydrogenmod.block

import io.netty.buffer.Unpooled
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.{Component, TranslatableComponent}
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.{InteractionHand, InteractionResult, MenuProvider}
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.{BlockGetter, Level}
import net.minecraft.world.level.block.{Block, EntityBlock, SoundType}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.{CollisionContext, VoxelShape}
import net.minecraftforge.fmllegacy.network.NetworkHooks
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.entity.ElectrolyzerBlockEntity
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu

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
    )
    with EntityBlock {
  override def getShape(
      p_51973_ : BlockState,
      p_51974_ : BlockGetter,
      p_51975_ : BlockPos,
      p_51976_ : CollisionContext
  ): VoxelShape =
    Block.box(0.0d, 0.0d, 0.0d, 16.0d, 32.0d, 16.0d)

  override def use(
      p_60503_ : BlockState,
      p_60504_ : Level,
      p_60505_ : BlockPos,
      p_60506_ : Player,
      p_60507_ : InteractionHand,
      p_60508_ : BlockHitResult
  ): InteractionResult = {
    p_60506_ match {
      case serverPlayer: ServerPlayer =>
        val extraData =
          new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(p_60505_)

        NetworkHooks.openGui(
          serverPlayer,
          new MenuProvider {
            override def getDisplayName: Component =
              new TranslatableComponent("gui.hydrogenmod.electrolyzer.title")

            override def createMenu(
                p_39954_ : Int,
                p_39955_ : Inventory,
                p_39956_ : Player
            ): AbstractContainerMenu = ElectrolyzerMenu.newInstance(
              p_39954_,
              p_39955_,
              extraData
            )
          },
          p_60505_
        )
      case _ =>
    }

    InteractionResult.SUCCESS
  }

  override def getMenuProvider(
      p_60563_ : BlockState,
      p_60564_ : Level,
      p_60565_ : BlockPos
  ): MenuProvider = {
    val blockEntity = p_60564_.getBlockEntity(p_60565_)
    blockEntity match {
      case menuProvider: MenuProvider =>
        menuProvider
      case _ =>
        null
    }
  }

  override def newBlockEntity(
      p_153215_ : BlockPos,
      p_153216_ : BlockState
  ): BlockEntity = ElectrolyzerBlockEntity.newInstance(p_153215_, p_153216_)
}

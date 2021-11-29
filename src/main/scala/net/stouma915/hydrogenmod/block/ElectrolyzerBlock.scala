package net.stouma915.hydrogenmod.block

import io.netty.buffer.Unpooled
import net.minecraft.core.{BlockPos, NonNullList}
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.{Component, TranslatableComponent}
import net.minecraft.server.level.{ServerLevel, ServerPlayer}
import net.minecraft.world.{
  Containers,
  InteractionHand,
  InteractionResult,
  MenuProvider
}
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.{ItemStack, Items}
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.{BlockGetter, Level}
import net.minecraft.world.level.block.{Block, EntityBlock, SoundType}
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.{BlockState, StateDefinition}
import net.minecraft.world.level.block.state.properties.{
  IntegerProperty,
  Property
}
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.{CollisionContext, VoxelShape}
import net.minecraftforge.fmllegacy.network.NetworkHooks
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.entity.ElectrolyzerBlockEntity
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.BatteryItem
import net.stouma915.hydrogenmod.recipe.electrolysis.ElectrolysisRecipeRegistry

import java.util.Random
import scala.jdk.CollectionConverters.*
import scala.util.control.Breaks.*

object ElectrolyzerBlock {

  final val WaterLevelProperty =
    IntegerProperty.create("water_level", 0, 9)
  final val ProgressProperty =
    IntegerProperty.create("progress", 0, 6)

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

  this.registerDefaultState(
    this.stateDefinition
      .any()
      .setValue(ElectrolyzerBlock.WaterLevelProperty, 0)
      .setValue(ElectrolyzerBlock.ProgressProperty, 0)
  )

  override def createBlockStateDefinition(
      p_49915_ : StateDefinition.Builder[Block, BlockState]
  ): Unit = p_49915_
    .add(ElectrolyzerBlock.WaterLevelProperty)
    .add(ElectrolyzerBlock.ProgressProperty)

  override def newBlockEntity(
      p_153215_ : BlockPos,
      p_153216_ : BlockState
  ): BlockEntity = ElectrolyzerBlockEntity.newInstance(p_153215_, p_153216_)

  override def onPlace(
      p_60566_ : BlockState,
      p_60567_ : Level,
      p_60568_ : BlockPos,
      p_60569_ : BlockState,
      p_60570_ : Boolean
  ): Unit =
    p_60567_.getBlockTicks.scheduleTick(p_60568_, this, 5)

  override def tick(
      p_60462_ : BlockState,
      p_60463_ : ServerLevel,
      p_60464_ : BlockPos,
      p_60465_ : Random
  ): Unit = {
    val blockEntity = p_60463_.getBlockEntity(p_60464_) match {
      case entity: ElectrolyzerBlockEntity =>
        entity
      case _ => throw new IllegalStateException
    }
    val items: List[ItemStack] = blockEntity.getItems.asScala.toList

    val isBatterySet = items(9).getItem == BatteryItem()
    val isInputCorrect = items
      .dropRight(10)
      .filterNot(elem => elem.isEmpty)
      .filterNot(elem => elem.getItem == Items.BUCKET)
      .forall(elem =>
        ElectrolysisRecipeRegistry.getAll.exists(_.isCorrectAsInput(elem))
      )
    val isSameItemInput = {
      val droppedItems = items
        .dropRight(10)
        .filterNot(elem => elem.isEmpty)
        .filterNot(elem => elem.getItem == Items.BUCKET)

      droppedItems.count(elem =>
        elem.sameItem(droppedItems.head)
      ) == droppedItems.length && droppedItems.nonEmpty
    }

    if (isBatterySet && isInputCorrect && isSameItemInput) {
      val electrolysisRecipe = ElectrolysisRecipeRegistry.getAll
        .find(elem =>
          elem.isCorrectAsInput(
            items
              .filterNot(elem => elem.isEmpty)
              .filterNot(elem => elem.getItem == Items.BUCKET)
              .head
          )
        )
        .getOrElse {
          throw new IllegalStateException()
        }
      val currentProgress = getProgress(p_60463_, p_60464_)

      if (currentProgress >= 6) {
        items(9).addDamage()
        setProgress(p_60462_, p_60463_, p_60464_, 0)
        breakable {
          items.dropRight(10).zipWithIndex.foreach {
            case (itemStack: ItemStack, index: Int)
                if !itemStack.isEmpty && itemStack.getItem != Items.BUCKET =>
              if (electrolysisRecipe.leaveBucketOfInput) {
                val bucketItemStack = Items.BUCKET.toGeneralItemStack
                blockEntity.setItems(
                  NonNullList
                    .of(null, items.updated(index, bucketItemStack).toArray: _*)
                )
              } else itemStack.setCount(itemStack.getCount - 1)
              break()
            case _ =>
          }
        }
      } else setProgress(p_60462_, p_60463_, p_60464_, currentProgress + 1)
    } else setProgress(p_60462_, p_60463_, p_60464_, 0)

    p_60463_.getBlockTicks.scheduleTick(p_60464_, this, 5)
  }

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

  override def onRemove(
      p_60515_ : BlockState,
      p_60516_ : Level,
      p_60517_ : BlockPos,
      p_60518_ : BlockState,
      p_60519_ : Boolean
  ): Unit = {
    if (p_60515_.getBlock != p_60518_.getBlock) {
      val blockEntity = p_60516_.getBlockEntity(p_60517_)
      blockEntity match {
        case entity: ElectrolyzerBlockEntity =>
          Containers.dropContents(p_60516_, p_60517_, entity)
          p_60516_.updateNeighbourForOutputSignal(p_60517_, this)
        case _ =>
      }
    }
  }

  private def getProgress(
      level: Level,
      blockPos: BlockPos
  ): Int =
    level
      .getBlockEntity(blockPos)
      .getBlockState
      .getValue(
        ElectrolyzerBlock.ProgressProperty.asInstanceOf[Property[Nothing]]
      )

  private def setProgress(
      blockState: BlockState,
      level: Level,
      blockPos: BlockPos,
      newProgress: Int
  ): Unit = {
    val newBlockState =
      blockState.setValue(ElectrolyzerBlock.ProgressProperty, newProgress)

    level.setBlock(blockPos, newBlockState, 3)
  }

}

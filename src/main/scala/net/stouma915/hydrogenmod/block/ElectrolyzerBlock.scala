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

  registerDefaultState(
    stateDefinition
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
    p_60567_.getBlockTicks.scheduleTick(p_60568_, this, 0)

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
    val items = blockEntity.getItems.asScala.toList.map(_.copy)
    val waterLevel = items.dropRight(10).count { itemStack =>
      ElectrolysisRecipeRegistry.getAll.exists(_.isCorrectAsInput(itemStack))
    }

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
      val inputItem = items
        .filterNot(elem => elem.isEmpty)
        .filterNot(elem => elem.getItem == Items.BUCKET)
        .head
      val electrolysisRecipe = ElectrolysisRecipeRegistry.getAll
        .find(elem => elem.isCorrectAsInput(inputItem))
        .getOrElse {
          throw new IllegalStateException()
        }

      if (
        canPlaceItems(
          electrolysisRecipe.getOutputItems(inputItem),
          items.drop(10)
        )
      ) {
        val currentProgress = getProgress(p_60463_, p_60464_)

        if (currentProgress >= 6) {
          var newItems = items

          val damagedBattery = newItems(9).copy
          damagedBattery.addDamage()
          newItems = newItems.updated(9, damagedBattery)

          updateState(p_60462_, p_60463_, p_60464_, 0, waterLevel)

          breakable {
            newItems.dropRight(10).zipWithIndex.foreach {
              case (itemStack: ItemStack, index: Int)
                  if !itemStack.isEmpty && itemStack.getItem != Items.BUCKET =>
                if (electrolysisRecipe.leaveBucketOfInput) {
                  val bucketItemStack = Items.BUCKET.toGeneralItemStack
                  newItems = newItems.updated(index, bucketItemStack)
                } else {
                  val newItem = itemStack.copy
                  newItem.setCount(newItem.getCount - 1)
                  newItems = newItems.updated(index, newItem)
                }
                break()
              case _ =>
            }
          }

          newItems = newItems
            .dropRight(9)
            .appendedAll(
              placeItems(
                electrolysisRecipe.getOutputItems(inputItem),
                newItems.drop(10).map(_.copy)
              )
            )

          blockEntity.setItems(NonNullList.of(null, newItems.toArray: _*))
        } else
          updateState(
            p_60462_,
            p_60463_,
            p_60464_,
            currentProgress + 1,
            waterLevel
          )
      } else updateState(p_60462_, p_60463_, p_60464_, 0, waterLevel)
    } else updateState(p_60462_, p_60463_, p_60464_, 0, waterLevel)

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

  private def placeItems(
      itemsToPlace: List[ItemStack],
      currentItems: List[ItemStack]
  ): List[ItemStack] = {
    if (itemsToPlace.isEmpty)
      return currentItems
    if (currentItems.isEmpty)
      return itemsToPlace

    require(itemsToPlace.length <= 9)
    require(currentItems.length <= 9)

    if (currentItems.length != 9) currentItems.appendedAll(itemsToPlace)
    else {
      val unstackableItemsToPlace = itemsToPlace.filterNot(_.isStackable)
      val stackableItemsToPlace = itemsToPlace.filter(_.isStackable)

      var overflowingItems = List[ItemStack]()

      def addItems(
          items: List[ItemStack],
          target: List[ItemStack]
      ): List[ItemStack] = {
        var itemIndex = 0
        target.zipWithIndex
          .map { case (itemStack: ItemStack, index: Int) =>
            if (itemStack.isEmpty) {
              itemIndex += 1
              (
                items.applyOrElse(itemIndex - 1, _ => itemStack),
                index
              )
            } else (itemStack, index)
          }
          .map(_._1)
      }

      val result = for {
        afterAddUnstackable <- Some {
          addItems(unstackableItemsToPlace, currentItems)
        }
        itemsStackedToMax <- Some {
          stackableItemsToPlace.filter(elem =>
            elem.getCount >= elem.getMaxStackSize
          )
        }
        afterAddStackedToMax <- Some {
          addItems(itemsStackedToMax, afterAddUnstackable)
        }
        itemsNotIncluded <- Some {
          stackableItemsToPlace
            .filterNot(elem => elem.getCount >= elem.getMaxStackSize)
            .filterNot(elem =>
              afterAddStackedToMax.exists(item => item.sameItem(elem))
            )
        }
        afterAddNotIncluded <- Some {
          addItems(itemsNotIncluded, afterAddStackedToMax)
        }
        itemsIncluded <- Some {
          stackableItemsToPlace
            .filterNot(elem => elem.getCount >= elem.getMaxStackSize)
            .filter(elem =>
              afterAddStackedToMax.exists(item => item.sameItem(elem))
            )
        }
        _ <- Some {
          itemsIncluded
            .foreach { itemStack =>
              val sameItems = afterAddNotIncluded
                .filter(item => item.sameItem(itemStack))
              val canBeStacked =
                sameItems.map(item => item.getMaxStackSize - item.getCount).sum

              // format: off
              if (itemStack.getCount == canBeStacked)
                sameItems.foreach(item => item.setCount(item.getMaxStackSize))
              else if (itemStack.getCount > canBeStacked) {
                sameItems.foreach(item => item.setCount(item.getMaxStackSize))
                val overflowingItem = itemStack
                overflowingItem.setCount(overflowingItem.getCount - canBeStacked)
                overflowingItems = overflowingItems.appended(overflowingItem)
              }
              else if (itemStack.getCount < canBeStacked) {
                while (itemStack.getCount > 0)
                  sameItems.foreach { item =>
                    item.setCount(item.getCount + 1)
                    itemStack.setCount(itemStack.getCount - 1)
                  }
              }
              // format: on
            }
        }
        afterAddOverflowingItems <- Some {
          addItems(overflowingItems, afterAddNotIncluded)
        }
      } yield afterAddOverflowingItems

      result.getOrElse(throw new IllegalStateException)
    }
  }

  private def canPlaceItems(
      itemsToPlace: List[ItemStack],
      currentItems: List[ItemStack]
  ): Boolean = {
    if (itemsToPlace.isEmpty || currentItems.isEmpty)
      return true

    require(itemsToPlace.length <= 9)
    require(currentItems.length <= 9)

    val numberOfEmptySlot =
      if (currentItems.length == 9)
        currentItems.count(_.isEmpty)
      else
        9 - currentItems.length
    val currentUnstackableItems = currentItems.filterNot(_.isStackable)
    val unstackableItemsToPlace = itemsToPlace.filterNot(_.isStackable)
    val currentStackableItems = currentItems.filter(_.isStackable)
    val stackableItemsToPlace = itemsToPlace.filter(_.isStackable)

    // format: off

    if (itemsToPlace.length <= numberOfEmptySlot)
      return true
    
    if (currentUnstackableItems.length == 9)
      return false

    if (unstackableItemsToPlace.length > numberOfEmptySlot)
      return false

    if (stackableItemsToPlace.isEmpty && unstackableItemsToPlace.length <= numberOfEmptySlot)
      return true
      
    if (unstackableItemsToPlace.isEmpty && !currentStackableItems.exists(elem => elem.getCount < elem.getMaxStackSize))
      return false
      
    if (unstackableItemsToPlace.isEmpty) {
      var count = 1
      val bool = stackableItemsToPlace.forall { elem =>
        val sameItems = currentStackableItems.filter(_.sameItem(elem))
        if (sameItems.isEmpty) {
          count += 1
          if (numberOfEmptySlot >= count - 1)
            true
          else 
            false
        } else {
          val b = sameItems.map(e => e.getMaxStackSize - e.getCount).sum >= elem.getCount
          if (b)
            true
          else {
            count += 1
            if (numberOfEmptySlot >= count - 1)
              true
            else false
          }
        }
      }
      
      if (bool)
        return true
    }
    
    if (unstackableItemsToPlace.nonEmpty && stackableItemsToPlace.nonEmpty) {
      if (unstackableItemsToPlace.length >= numberOfEmptySlot)
        return false
        
      val numberOfEmptySlotAfterPlacingUnstackable = numberOfEmptySlot - unstackableItemsToPlace.length
      if (numberOfEmptySlotAfterPlacingUnstackable <= 0)
        return false
        
      var count = 1
      val bool = stackableItemsToPlace.forall { elem =>
        val sameItems = currentStackableItems.filter(_.sameItem(elem))
        if (sameItems.isEmpty) {
          count += 1
          if (numberOfEmptySlotAfterPlacingUnstackable >= count - 1)
            true
          else
            false
        } else {
          if (sameItems.map(e => e.getMaxStackSize - e.getCount).sum >= elem.getCount)
            true
          else {
            count += 1
            if (numberOfEmptySlot >= count - 1)
              true
            else false
          }
        }
      }
      
      if (bool)
        return true
    }

    // format: on

    false
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

  private def updateState(
      blockState: BlockState,
      level: Level,
      blockPos: BlockPos,
      newProgress: Int,
      newWaterLevel: Int
  ): Unit = {
    val newBlockState =
      blockState
        .setValue(ElectrolyzerBlock.ProgressProperty, newProgress)
        .setValue(ElectrolyzerBlock.WaterLevelProperty, newWaterLevel)

    level.setBlock(blockPos, newBlockState, 3)
  }

}

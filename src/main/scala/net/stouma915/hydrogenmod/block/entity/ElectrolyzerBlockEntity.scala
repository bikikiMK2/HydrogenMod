package net.stouma915.hydrogenmod.block.entity

import io.netty.buffer.Unpooled
import net.minecraft.core.{BlockPos, Direction, NonNullList}
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.{Connection, FriendlyByteBuf}
import net.minecraft.network.chat.{Component, TextComponent}
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.{ContainerHelper, WorldlyContainer}
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.{
  BlockEntityType,
  RandomizableContainerBlockEntity
}
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.{
  CapabilityItemHandler,
  IItemHandler,
  IItemHandlerModifiable
}
import net.minecraftforge.items.wrapper.SidedInvWrapper
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu
import net.stouma915.hydrogenmod.implicits.*

import scala.jdk.CollectionConverters.*

object ElectrolyzerBlockEntity {
  private val instance: BlockEntityType[_] =
    BlockEntityType.Builder
      .of(
        new BlockEntityType.BlockEntitySupplier[ElectrolyzerBlockEntity] {
          override def create(p_155268_ : BlockPos, p_155269_ : BlockState)
              : ElectrolyzerBlockEntity =
            new ElectrolyzerBlockEntity(p_155268_, p_155269_)
        },
        ElectrolyzerBlock()
      )
      .build(null)
      .setRegistryName(HydrogenMod.ModId, "electrolyzer")

  def apply(): BlockEntityType[_] = instance

  private[hydrogenmod] def newInstance(
      blockPos: BlockPos,
      blockState: BlockState
  ): ElectrolyzerBlockEntity = new ElectrolyzerBlockEntity(blockPos, blockState)
}

sealed class ElectrolyzerBlockEntity private (
    blockPos: BlockPos,
    blockState: BlockState
) extends RandomizableContainerBlockEntity(
      ElectrolyzerBlockEntity(),
      blockPos,
      blockState
    )
    with WorldlyContainer {
  private var itemStacks = NonNullList.withSize[ItemStack](9, ItemStack.EMPTY)
  private val handlers: Array[LazyOptional[IItemHandlerModifiable]] =
    SidedInvWrapper.create(this, Direction.values: _*)

  override def load(p_155080_ : CompoundTag): Unit = {
    super.load(p_155080_)
    if (!this.tryLoadLootTable(p_155080_))
      itemStacks = NonNullList.withSize(this.getContainerSize, ItemStack.EMPTY)

    ContainerHelper.loadAllItems(
      p_155080_,
      itemStacks
    )
  }

  override def save(p_58637_ : CompoundTag): CompoundTag = {
    super.save(p_58637_)
    if (!this.trySaveLootTable(p_58637_))
      ContainerHelper.saveAllItems(p_58637_, itemStacks)

    p_58637_
  }

  override def getUpdatePacket: ClientboundBlockEntityDataPacket =
    new ClientboundBlockEntityDataPacket(
      this.worldPosition,
      0,
      this.getUpdateTag
    )

  override def getUpdateTag: CompoundTag = this.save(new CompoundTag())

  override def onDataPacket(
      net: Connection,
      pkt: ClientboundBlockEntityDataPacket
  ): Unit = this.load(pkt.getTag)

  override def isEmpty: Boolean =
    !itemStacks.asScala.toList.map(_.isEmpty).contains(false)

  override def getMaxStackSize: Int = 64

  override def getItems: NonNullList[ItemStack] = itemStacks

  override def setItems(p_59625_ : NonNullList[ItemStack]): Unit = itemStacks =
    p_59625_

  override def canPlaceItem(p_18952_ : Int, p_18953_ : ItemStack): Boolean =
    true

  override def getSlotsForFace(p_19238_ : Direction): Array[Int] =
    (0 to this.getContainerSize).toArray

  override def canPlaceItemThroughFace(
      p_19235_ : Int,
      p_19236_ : ItemStack,
      p_19237_ : Direction
  ): Boolean = canPlaceItem(p_19235_, p_19236_)

  override def canTakeItemThroughFace(
      p_19239_ : Int,
      p_19240_ : ItemStack,
      p_19241_ : Direction
  ): Boolean = true

  override def getCapability[T](
      cap: Capability[T],
      side: Direction
  ): LazyOptional[T] =
    if (
      !this.remove && side.nonNull && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    )
      handlers(side.ordinal).cast
    else
      super.getCapability(cap, side)

  override def setRemoved(): Unit = {
    super.setRemoved()
    handlers.foreach { handler =>
      handler.invalidate()
    }
  }

  override def createMenu(
      p_58627_ : Int,
      p_58628_ : Inventory
  ): AbstractContainerMenu =
    ElectrolyzerMenu.newInstance(
      p_58627_,
      p_58628_,
      new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition)
    )

  override def getDefaultName: Component = new TextComponent("electrolyzer")

  override def getContainerSize: Int = 9
}

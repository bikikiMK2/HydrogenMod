package net.stouma915.hydrogenmod.block.entity

import io.netty.buffer.Unpooled
import net.minecraft.core.{BlockPos, Direction, NonNullList}
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.{Component, TextComponent}
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.{
  BlockEntityType,
  RandomizableContainerBlockEntity
}
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.{ContainerHelper, WorldlyContainer}
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.wrapper.SidedInvWrapper
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandlerModifiable}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu
import net.stouma915.hydrogenmod.implicits.*

object ElectrolyzerBlockEntity {

  private val instance: BlockEntityType[_] =
    BlockEntityType.Builder
      .of(
        new ElectrolyzerBlockEntity(_, _),
        ElectrolyzerBlock()
      )
      .build(null)
      .setRegistryName(HydrogenMod.ModId, "electrolyzer")

  def apply(): BlockEntityType[_] = instance

}

sealed class ElectrolyzerBlockEntity private[block] (
    blockPos: BlockPos,
    blockState: BlockState
) extends RandomizableContainerBlockEntity(
      ElectrolyzerBlockEntity(),
      blockPos,
      blockState
    )
    with WorldlyContainer {

  private val handlers: Array[LazyOptional[IItemHandlerModifiable]] =
    SidedInvWrapper.create(this, Direction.values: _*)
  private var itemStacks = NonNullList.withSize[ItemStack](19, ItemStack.EMPTY)

  override def load(p_155080_ : CompoundTag): Unit = {
    super.load(p_155080_)
    itemStacks = NonNullList.withSize(getContainerSize, ItemStack.EMPTY)

    if (!tryLoadLootTable(p_155080_) && p_155080_.contains("Items"))
      ContainerHelper.loadAllItems(p_155080_, itemStacks)
  }

  override def getContainerSize: Int = 19

  override def saveAdditional(p_187461_ : CompoundTag): Unit = {
    super.saveAdditional(p_187461_)
    if (!trySaveLootTable(p_187461_))
      ContainerHelper.saveAllItems(p_187461_, itemStacks, false)
  }

  override def getMaxStackSize: Int = 64

  override def getItems: NonNullList[ItemStack] = itemStacks

  override def setItems(p_59625_ : NonNullList[ItemStack]): Unit = itemStacks =
    p_59625_

  override def getSlotsForFace(p_19238_ : Direction): Array[Int] =
    (0 to getContainerSize).toArray

  override def canPlaceItemThroughFace(
      p_19235_ : Int,
      p_19236_ : ItemStack,
      p_19237_ : Direction
  ): Boolean = canPlaceItem(p_19235_, p_19236_)

  override def canPlaceItem(p_18952_ : Int, p_18953_ : ItemStack): Boolean =
    true

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
      !remove && side.nonNull && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
    )
      handlers(side.ordinal).cast
    else
      super.getCapability(cap, side)

  override def setRemoved(): Unit = {
    super.setRemoved()
    handlers.foreach(_.invalidate())
  }

  override def createMenu(
      p_58627_ : Int,
      p_58628_ : Inventory
  ): AbstractContainerMenu =
    new ElectrolyzerMenu(
      p_58627_,
      p_58628_,
      new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(worldPosition)
    )

  override def getDefaultName: Component = new TextComponent("electrolyzer")

}

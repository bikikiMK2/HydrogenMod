package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType, Slot}
import net.minecraft.world.item.{ItemStack, Items}
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.fmllegacy.network.IContainerFactory
import net.minecraftforge.items.{
  CapabilityItemHandler,
  IItemHandler,
  SlotItemHandler
}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.BatteryItem
import net.stouma915.hydrogenmod.recipe.electrolysis.ElectrolysisRecipeRegistry

import java.util.function.Supplier
import scala.collection.{immutable, mutable}
import scala.util.chaining.*
import scala.util.control.Breaks.*

object ElectrolyzerMenu {

  private val instance: MenuType[ElectrolyzerMenu] = {
    val menuType = new MenuType[ElectrolyzerMenu](
      (
          (
              id: Int,
              inventory: Inventory,
              extraData: FriendlyByteBuf
          ) => new ElectrolyzerMenu(id, inventory, extraData)
      ): IContainerFactory[ElectrolyzerMenu]
    )

    menuType.setRegistryName(HydrogenMod.ModId, "electrolyzer_menu")
    menuType
  }

  def apply(): MenuType[ElectrolyzerMenu] = instance

  private[hydrogenmod] def newInstance(
      id: Int,
      inventory: Inventory,
      extraData: FriendlyByteBuf
  ): ElectrolyzerMenu = new ElectrolyzerMenu(id, inventory, extraData)

}

sealed class ElectrolyzerMenu private (
    id: Int,
    inventory: Inventory,
    extraData: FriendlyByteBuf
) extends AbstractContainerMenu(ElectrolyzerMenu(), id)
    with Supplier[Map[Int, Slot]] {

  private val customSlots = mutable.Map[Int, Slot]()
  private val blockPos: BlockPos = extraData.readBlockPos()
  private var iItemHandler: IItemHandler = _

  inventory.player.level
    .getBlockEntity(blockPos)
    .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
    .ifPresent { capability =>
      iItemHandler = capability
    }

  if (iItemHandler.isNull)
    throw new IllegalStateException("IItemHandler is null.")

  customSlots
    .tap(
      _.put(
        0,
        this.addSlot(new SlotItemHandler(iItemHandler, 0, 21, 18) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        1,
        this.addSlot(new SlotItemHandler(iItemHandler, 1, 39, 18) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        2,
        this.addSlot(new SlotItemHandler(iItemHandler, 2, 57, 18) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        3,
        this.addSlot(new SlotItemHandler(iItemHandler, 3, 21, 36) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        4,
        this.addSlot(new SlotItemHandler(iItemHandler, 4, 39, 36) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        5,
        this.addSlot(new SlotItemHandler(iItemHandler, 5, 57, 36) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        6,
        this.addSlot(new SlotItemHandler(iItemHandler, 6, 21, 54) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        7,
        this.addSlot(new SlotItemHandler(iItemHandler, 7, 39, 54) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )
    .tap(
      _.put(
        8,
        this.addSlot(new SlotItemHandler(iItemHandler, 8, 57, 54) {
          override def setChanged(): Unit = setWaterLevel()
        })
      )
    )

  customSlots.put(
    9,
    this.addSlot(new SlotItemHandler(iItemHandler, 9, 80, 18) {
      override def mayPlace(stack: ItemStack): Boolean =
        stack.getItem == BatteryItem()
    })
  )

  customSlots
    .tap(
      _.put(
        10,
        this.addSlot(new SlotItemHandler(iItemHandler, 10, 103, 18) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        11,
        this.addSlot(new SlotItemHandler(iItemHandler, 11, 121, 18) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        12,
        this.addSlot(new SlotItemHandler(iItemHandler, 12, 139, 18) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        13,
        this.addSlot(new SlotItemHandler(iItemHandler, 13, 103, 36) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        14,
        this.addSlot(new SlotItemHandler(iItemHandler, 14, 121, 36) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        15,
        this.addSlot(new SlotItemHandler(iItemHandler, 15, 139, 36) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        16,
        this.addSlot(new SlotItemHandler(iItemHandler, 16, 103, 54) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        17,
        this.addSlot(new SlotItemHandler(iItemHandler, 17, 121, 54) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )
    .tap(
      _.put(
        18,
        this.addSlot(new SlotItemHandler(iItemHandler, 18, 139, 54) {
          override def mayPlace(stack: ItemStack): Boolean = false
        })
      )
    )

  (0 to 2).foreach { a =>
    (0 to 8).foreach { b =>
      this.addSlot(
        new Slot(inventory, b + (a + 1) * 9, 0 + 8 + b * 18, 0 + 84 + a * 18)
      )
    }
  }
  (0 to 8).foreach { a =>
    this.addSlot(new Slot(inventory, a, 0 + 8 + a * 18, 0 + 142))
  }

  private def setWaterLevel(): Unit = {
    val waterLevel = customSlots.count {
      case (index: Int, slot: Slot) if (0 to 8).contains(index) =>
        if (slot.hasItem && slot.getItem.nonNull && !slot.getItem.isEmpty) {
          val itemStack = slot.getItem

          ElectrolysisRecipeRegistry.getAll.exists(
            _.isCorrectAsInput(itemStack)
          )
        } else false
      case _ =>
        false
    }

    val blockEntity = inventory.player.level.getBlockEntity(blockPos)
    val newBlockState = blockEntity.getBlockState.setValue(
      ElectrolyzerBlock.WaterLevelProperty,
      waterLevel
    )
    inventory.player.level.setBlock(blockPos, newBlockState, 3)
  }

  override def quickMoveStack(p_38941_ : Player, p_38942_ : Int): ItemStack = {
    var itemStack = ItemStack.EMPTY
    val slot = this.slots.get(p_38942_)

    if (slot.nonNull && slot.hasItem) {
      val itemStack1 = slot.getItem
      itemStack = itemStack1.copy()

      if (p_38942_ < 0) {
        if (
          !this.moveItemStackTo(
            itemStack1,
            0,
            this.slots.size(),
            p_38907_ = true
          )
        )
          return ItemStack.EMPTY

        slot.onQuickCraft(itemStack1, itemStack)
      } else if (!this.moveItemStackTo(itemStack1, 0, 0, p_38907_ = false)) {
        if (p_38942_ < 0 + 27) {
          if (
            !this.moveItemStackTo(
              itemStack1,
              0 + 27,
              this.slots.size(),
              p_38907_ = true
            )
          )
            return ItemStack.EMPTY
        } else {
          if (!this.moveItemStackTo(itemStack1, 0, 0 + 27, p_38907_ = false))
            return ItemStack.EMPTY
        }
      }

      if (itemStack1.getCount == 0)
        slot.set(ItemStack.EMPTY)
      else
        slot.setChanged()

      if (itemStack1.getCount == itemStack.getCount)
        return ItemStack.EMPTY

      slot.onTake(p_38941_, itemStack1)
    }

    itemStack
  }

  override def moveItemStackTo(
      p_38904_ : ItemStack,
      p_38905_ : Int,
      p_38906_ : Int,
      p_38907_ : Boolean
  ): Boolean = {
    var canMove = false
    var i = if (p_38907_) p_38906_ - 1 else p_38905_

    if (p_38904_.isStackable)
      breakable {
        while (!p_38904_.isEmpty) {
          if (p_38907_ && i < p_38905_)
            break()
          else if (i >= p_38906_)
            break()

          val slot = this.slots.get(i)
          val itemStack = slot.getItem

          if (
            slot.mayPlace(itemStack) && itemStack.isEmpty && ItemStack
              .isSameItemSameTags(p_38904_, itemStack)
          ) {
            val j = itemStack.getCount + p_38904_.getCount
            val maxSize =
              math.min(slot.getMaxStackSize, p_38904_.getMaxStackSize)

            if (j <= maxSize) {
              p_38904_.setCount(0)
              itemStack.setCount(j)
              slot.set(itemStack)
              canMove = true
            } else if (itemStack.getCount < maxSize) {
              p_38904_.shrink(maxSize - itemStack.getCount)
              itemStack.setCount(maxSize)
              slot.set(itemStack)
              canMove = true
            }
          }

          if (p_38907_)
            i -= 1
          else
            i += 1
        }
      }

    if (!p_38904_.isEmpty) {
      if (p_38907_)
        i = p_38906_ - 1
      else
        i = p_38905_

      breakable {
        while (true) {
          if (p_38907_ && i < p_38905_)
            break()
          else if (i >= p_38906_)
            break()

          val slot = this.slots.get(i)
          val itemStack = slot.getItem

          if (itemStack.isEmpty && slot.mayPlace(p_38904_)) {
            if (p_38904_.getCount > slot.getMaxStackSize)
              slot.set(p_38904_.split(slot.getMaxStackSize))
            else
              slot.set(p_38904_.split(p_38904_.getCount))

            slot.setChanged()
            canMove = true
            break()
          }

          if (p_38907_)
            i -= 1
          else
            i += 1
        }
      }
    }

    canMove
  }

  override def stillValid(p_38874_ : Player): Boolean = true

  override def get(): Map[Int, Slot] = immutable.Map.from(customSlots)

  private[gui] def getProgress: Int = {
    val blockEntity = inventory.player.level.getBlockEntity(blockPos)
    blockEntity.getBlockState.getValue(
      ElectrolyzerBlock.ProgressProperty.asInstanceOf[Property[Nothing]]
    )
  }

}

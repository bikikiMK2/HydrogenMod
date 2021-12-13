package net.stouma915.hydrogenmod.meta.gui.menu

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType, Slot}
import net.minecraft.world.item.ItemStack
import net.stouma915.hydrogenmod.implicits.*

import java.util.function.Supplier
import scala.util.control.Breaks.*

abstract class GUIMenu(
    id: Int,
    inventory: Inventory,
    extraData: FriendlyByteBuf,
    menuType: MenuType[_]
) extends AbstractContainerMenu(menuType, id)
    with Supplier[Map[Int, Slot]] {

  override def quickMoveStack(p_38941_ : Player, p_38942_ : Int): ItemStack = {
    var itemStack = ItemStack.EMPTY
    val slot = slots.get(p_38942_)

    if (slot.nonNull && slot.hasItem) {
      val itemStack1 = slot.getItem
      itemStack = itemStack1.copy()

      if (p_38942_ < 0) {
        if (
          !moveItemStackTo(
            itemStack1,
            0,
            slots.size(),
            p_38907_ = true
          )
        )
          return ItemStack.EMPTY

        slot.onQuickCraft(itemStack1, itemStack)
      } else if (!moveItemStackTo(itemStack1, 0, 0, p_38907_ = false)) {
        if (p_38942_ < 0 + 27) {
          if (
            !moveItemStackTo(
              itemStack1,
              0 + 27,
              slots.size(),
              p_38907_ = true
            )
          )
            return ItemStack.EMPTY
        } else {
          if (!moveItemStackTo(itemStack1, 0, 0 + 27, p_38907_ = false))
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

          val slot = slots.get(i)
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

          val slot = slots.get(i)
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

}

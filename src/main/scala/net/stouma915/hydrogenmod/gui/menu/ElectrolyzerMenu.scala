package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType, Slot}
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fmllegacy.network.IContainerFactory
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.implicits._

import java.util.function.Supplier

object ElectrolyzerMenu {
  private val instance: MenuType[ElectrolyzerMenu] = {
    val containerFactory: IContainerFactory[ElectrolyzerMenu] =
      (id, inventory, _) => new ElectrolyzerMenu(id, inventory)
    val menuType = new MenuType[ElectrolyzerMenu](containerFactory)

    menuType.setRegistryName(HydrogenMod.ModId, "electrolyzer_menu")
    menuType
  }

  def apply(): MenuType[ElectrolyzerMenu] = instance
}

private[hydrogenmod] class ElectrolyzerMenu(
    id: Int,
    inventory: Inventory
) extends AbstractContainerMenu(ElectrolyzerMenu(), id)
    with Supplier[Map[Int, Slot]] {
  private val customSlots = Map[Int, Slot]()

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

  override def stillValid(p_38874_ : Player): Boolean = true

  override def quickMoveStack(p_38941_ : Player, p_38942_ : Int): ItemStack = {
    var itemStack = ItemStack.EMPTY
    val slot = this.slots.get(p_38942_)

    if (slot.nonNull && slot.hasItem) {
      val itemStack1 = slot.getItem
      itemStack = itemStack1.copy()

      if (p_38942_ < 0) {
        if (!this.moveItemStackTo(itemStack1, 0, this.slots.size(), true))
          return ItemStack.EMPTY

        slot.onQuickCraft(itemStack1, itemStack)
      } else if (!this.moveItemStackTo(itemStack1, 0, 0, false)) {
        if (p_38942_ < 0 + 27) {
          if (
            !this.moveItemStackTo(itemStack1, 0 + 27, this.slots.size(), true)
          )
            return ItemStack.EMPTY
        } else {
          if (!this.moveItemStackTo(itemStack1, 0, 0 + 27, false))
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

  override def get(): Map[Int, Slot] = customSlots
}

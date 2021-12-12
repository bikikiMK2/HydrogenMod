package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{MenuType, Slot}
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.properties.Property
import net.minecraftforge.items.{
  CapabilityItemHandler,
  IItemHandler,
  SlotItemHandler
}
import net.minecraftforge.network.IContainerFactory
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.BatteryItem
import net.stouma915.hydrogenmod.meta.gui.menu.GUIMenu

import scala.collection.{immutable, mutable}

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

}

sealed class ElectrolyzerMenu private[hydrogenmod] (
    id: Int,
    inventory: Inventory,
    extraData: FriendlyByteBuf
) extends GUIMenu(id, inventory, extraData, ElectrolyzerMenu()) {

  private val customSlots = mutable.Map[Int, Slot]()
  private val blockPos = extraData.readBlockPos()
  private var iItemHandler: IItemHandler = _

  inventory.player.level
    .getBlockEntity(blockPos)
    .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
    .ifPresent { capability =>
      iItemHandler = capability
    }

  if (iItemHandler.isNull)
    throw new IllegalStateException("Couldn't get the inventory capability.")

  // format: off
  private class CustomSlot(val index: Int, val x: Int, val y: Int, val mayPlace: ItemStack => Boolean)
  private case class InputSlot(override val index: Int, override val x: Int, override val y: Int) extends CustomSlot(index, x, y,  _ => true)
  private case class LimitedSlot(override val index: Int, override val x: Int, override val y: Int, override val mayPlace: ItemStack => Boolean) extends CustomSlot(index, x, y, mayPlace)
  private case class OutputSlot(override val index: Int, override val x: Int, override val y: Int) extends CustomSlot(index, x, y, _ => false)
  // format: on

  List[CustomSlot](
    InputSlot(0, 21, 18),
    InputSlot(1, 39, 18),
    InputSlot(2, 57, 18),
    InputSlot(3, 21, 36),
    InputSlot(4, 39, 36),
    InputSlot(5, 57, 36),
    InputSlot(6, 21, 54),
    InputSlot(7, 39, 54),
    InputSlot(8, 57, 54),
    LimitedSlot(9, 80, 18, _.getItem == BatteryItem()),
    OutputSlot(10, 103, 18),
    OutputSlot(11, 121, 18),
    OutputSlot(12, 139, 18),
    OutputSlot(13, 103, 36),
    OutputSlot(14, 121, 36),
    OutputSlot(15, 139, 36),
    OutputSlot(16, 103, 54),
    OutputSlot(17, 121, 54),
    OutputSlot(18, 139, 54)
  ).foreach { customSlot =>
    val slotItemHandler = new SlotItemHandler(
      iItemHandler,
      customSlot.index,
      customSlot.x,
      customSlot.y
    ) {
      override def mayPlace(stack: ItemStack): Boolean =
        customSlot.mayPlace(stack)
    }

    customSlots.put(customSlot.index, addSlot(slotItemHandler))
  }

  (0 to 2).foreach { a =>
    (0 to 8).foreach { b =>
      addSlot(
        new Slot(inventory, b + (a + 1) * 9, 0 + 8 + b * 18, 0 + 84 + a * 18)
      )
    }
  }
  (0 to 8).foreach { a =>
    addSlot(new Slot(inventory, a, 0 + 8 + a * 18, 0 + 142))
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

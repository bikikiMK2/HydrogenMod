package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType, Slot}
import net.minecraft.world.item.ItemStack
import net.minecraftforge.fmllegacy.network.IContainerFactory
import net.minecraftforge.items.{
  CapabilityItemHandler,
  IItemHandler,
  SlotItemHandler
}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.implicits.*

import java.util.function.Supplier
import scala.collection.{immutable, mutable}
import scala.util.chaining.*

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

private[hydrogenmod] class ElectrolyzerMenu(
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
    .tap(_.put(0, this.addSlot(new SlotItemHandler(iItemHandler, 0, 21, 18))))
    .tap(_.put(1, this.addSlot(new SlotItemHandler(iItemHandler, 1, 21, 36))))
    .tap(_.put(2, this.addSlot(new SlotItemHandler(iItemHandler, 2, 21, 54))))
    .tap(_.put(3, this.addSlot(new SlotItemHandler(iItemHandler, 3, 39, 18))))
    .tap(_.put(4, this.addSlot(new SlotItemHandler(iItemHandler, 4, 39, 36))))
    .tap(_.put(5, this.addSlot(new SlotItemHandler(iItemHandler, 5, 39, 54))))
    .tap(_.put(6, this.addSlot(new SlotItemHandler(iItemHandler, 6, 57, 18))))
    .tap(_.put(7, this.addSlot(new SlotItemHandler(iItemHandler, 7, 57, 36))))
    .tap(_.put(8, this.addSlot(new SlotItemHandler(iItemHandler, 8, 57, 54))))

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

  override def get(): Map[Int, Slot] = immutable.Map.from(customSlots)
}

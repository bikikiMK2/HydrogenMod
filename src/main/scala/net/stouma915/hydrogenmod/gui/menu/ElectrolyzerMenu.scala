package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.world.entity.player.{Inventory, Player}
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType, Slot}
import net.minecraftforge.fmllegacy.network.IContainerFactory
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.implicits.*

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

  override def get(): Map[Int, Slot] = customSlots
}

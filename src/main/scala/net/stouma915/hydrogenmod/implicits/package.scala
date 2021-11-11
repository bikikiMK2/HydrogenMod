package net.stouma915.hydrogenmod

import net.minecraft.world.item.{Item, ItemStack}

package object implicits {
  implicit class AnyRefOps(anyRef: AnyRef) {
    def isNull: Boolean = anyRef == null

    def nonNull: Boolean = !isNull
  }

  implicit class ItemOps(item: Item) {
    def asItemStack: ItemStack = new ItemStack(item)
  }
}

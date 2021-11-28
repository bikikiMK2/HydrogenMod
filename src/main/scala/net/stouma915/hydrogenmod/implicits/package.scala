package net.stouma915.hydrogenmod

import net.minecraft.core.BlockPos
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.{Entity, LivingEntity}
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.{Item, ItemStack}

package object implicits {

  implicit class AnyRefOps(anyRef: AnyRef) {
    def isNull: Boolean = anyRef == null

    def nonNull: Boolean = !isNull
  }

  implicit class ItemOps(item: Item) {
    def toGeneralItemStack: ItemStack = new ItemStack(item)

    def canDestroy: Boolean = item.isDamageable(toGeneralItemStack)
  }

  implicit class ItemStackOps(itemStack: ItemStack) {
    def destroyItem(livingEntity: LivingEntity): Unit =
      itemStack.hurtAndBreak(
        itemStack.getMaxDamage,
        livingEntity,
        (_: LivingEntity) => {}
      )
  }

  implicit class EntityOps(entity: Entity) {
    def getPos: BlockPos = new BlockPos(entity.getX, entity.getY, entity.getZ)
  }

  implicit class LivingEntityPos(livingEntity: LivingEntity) {
    def killWithCause(cause: DamageSource): Unit =
      livingEntity.hurt(cause, livingEntity.getMaxHealth)
  }

  implicit class SlotOps(slot: Slot) {
    def contains(item: Item): Boolean =
      slot.hasItem && slot.getItem.nonNull && !slot.getItem.isEmpty && slot.getItem.getItem == item
  }

}

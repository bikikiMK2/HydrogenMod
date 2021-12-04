package net.stouma915.hydrogenmod

import net.minecraft.core.BlockPos
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.{Entity, LivingEntity}
import net.minecraft.world.item.{Item, ItemStack}

package object implicits {

  implicit class AnyRefOps(anyRef: AnyRef) {

    def nonNull: Boolean = !isNull

    def isNull: Boolean = anyRef == null

  }

  implicit class ItemOps(item: Item) {

    def canDestroy: Boolean = item.isDamageable(toGeneralItemStack)

    def toGeneralItemStack: ItemStack = new ItemStack(item)

  }

  implicit class ItemStackOps(itemStack: ItemStack) {

    def destroyItem(livingEntity: LivingEntity): Unit =
      itemStack.hurtAndBreak(
        itemStack.getMaxDamage,
        livingEntity,
        (_: LivingEntity) => {}
      )

    def addDamage(): Unit =
      if (itemStack.getDamageValue >= itemStack.getMaxDamage - 1)
        itemStack.setCount(itemStack.getCount - 1)
      else
        itemStack.setDamageValue(itemStack.getDamageValue + 1)

  }

  implicit class EntityOps(entity: Entity) {

    def getPos: BlockPos = new BlockPos(entity.getX, entity.getY, entity.getZ)

  }

  implicit class LivingEntityPos(livingEntity: LivingEntity) {

    def killWithCause(cause: DamageSource): Unit =
      livingEntity.hurt(cause, livingEntity.getMaxHealth)

  }

}

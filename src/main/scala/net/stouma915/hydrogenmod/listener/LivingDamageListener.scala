package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.{EquipmentSlot, LivingEntity}
import net.minecraft.world.level.{Explosion, ExplosionDamageCalculator}
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.armor.item.{
  HydrogenBootsArmorItem,
  HydrogenChestplateArmorItem,
  HydrogenHelmetArmorItem,
  HydrogenLeggingsArmorItem
}
import net.stouma915.hydrogenmod.damagesource.HydrogenExplosionDamageSource

import java.util.function.Consumer

class LivingDamageListener {
  @SubscribeEvent
  def onLivingDamage(event: LivingDamageEvent): Unit =
    if (event.getSource.isFire) {

      import EquipmentSlot._

      val helmet =
        event.getEntityLiving.getItemBySlot(HEAD).getItem
      val chestplate =
        event.getEntityLiving.getItemBySlot(CHEST).getItem
      val leggings =
        event.getEntityLiving.getItemBySlot(LEGS).getItem
      val boots =
        event.getEntityLiving.getItemBySlot(FEET).getItem

      if (
        helmet == HydrogenHelmetArmorItem() ||
        chestplate == HydrogenChestplateArmorItem() ||
        leggings == HydrogenLeggingsArmorItem() ||
        boots == HydrogenBootsArmorItem()
      ) {
        val destroyArmor = (e: EquipmentSlot) =>
          event.getEntityLiving
            .getItemBySlot(e)
            .hurtAndBreak(
              event.getEntityLiving.getItemBySlot(e).getMaxDamage,
              event.getEntityLiving,
              (_: LivingEntity) => {}
            )

        if (helmet == HydrogenHelmetArmorItem())
          destroyArmor(HEAD)
        if (chestplate == HydrogenChestplateArmorItem())
          destroyArmor(CHEST)
        if (leggings == HydrogenLeggingsArmorItem())
          destroyArmor(LEGS)
        if (boots == HydrogenBootsArmorItem())
          destroyArmor(FEET)

        event.getEntityLiving.level.explode(
          null,
          HydrogenExplosionDamageSource(),
          new ExplosionDamageCalculator,
          event.getEntityLiving.getX,
          event.getEntityLiving.getY,
          event.getEntityLiving.getZ,
          10.0f,
          false,
          Explosion.BlockInteraction.BREAK
        )
      }
    }
}

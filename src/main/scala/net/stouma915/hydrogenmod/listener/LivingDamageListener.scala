package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.EquipmentSlot
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

class LivingDamageListener {
  @SubscribeEvent
  def onLivingDamage(event: LivingDamageEvent): Unit =
    if (event.getSource.isFire) {
      val helmet =
        event.getEntityLiving.getItemBySlot(EquipmentSlot.HEAD).getItem
      val chestplate =
        event.getEntityLiving.getItemBySlot(EquipmentSlot.CHEST).getItem
      val leggings =
        event.getEntityLiving.getItemBySlot(EquipmentSlot.LEGS).getItem
      val boots =
        event.getEntityLiving.getItemBySlot(EquipmentSlot.FEET).getItem

      if (
        helmet == HydrogenHelmetArmorItem() ||
        chestplate == HydrogenChestplateArmorItem() ||
        leggings == HydrogenLeggingsArmorItem() ||
        boots == HydrogenBootsArmorItem()
      )
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

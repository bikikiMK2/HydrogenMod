package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.EquipmentSlot
import net.minecraftforge.event.entity.living.LivingDamageEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.armor.item.{
  HydrogenBootsArmorItem,
  HydrogenChestplateArmorItem,
  HydrogenHelmetArmorItem,
  HydrogenLeggingsArmorItem
}
import net.stouma915.hydrogenmod.implicits._
import net.stouma915.hydrogenmod.util.Util

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
        if (helmet == HydrogenHelmetArmorItem())
          event.getEntityLiving
            .getItemBySlot(HEAD)
            .destroyItem(event.getEntityLiving)
        if (chestplate == HydrogenChestplateArmorItem())
          event.getEntityLiving
            .getItemBySlot(CHEST)
            .destroyItem(event.getEntityLiving)
        if (leggings == HydrogenLeggingsArmorItem())
          event.getEntityLiving
            .getItemBySlot(LEGS)
            .destroyItem(event.getEntityLiving)
        if (boots == HydrogenBootsArmorItem())
          event.getEntityLiving
            .getItemBySlot(FEET)
            .destroyItem(event.getEntityLiving)

        Util.performHydrogenExplosion(
          event.getEntityLiving.level,
          event.getEntityLiving.getPos
        )
      }
    }
}

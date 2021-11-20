package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.EquipmentSlot
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.armor.item.{
  HydrogenHelmetArmorItem,
  OxygenHelmetArmorItem
}
import net.stouma915.hydrogenmod.damagesource.SuffocationDamageSource
import net.stouma915.hydrogenmod.implicits._
import net.stouma915.hydrogenmod.potion.effect.{
  HydrogenWaterEffect,
  OxygenWaterEffect
}

import scala.jdk.CollectionConverters._

class LivingUpdateListener {
  @SubscribeEvent
  def onLivingUpdate(event: LivingUpdateEvent): Unit = {
    event.getEntityLiving.getActiveEffects.asScala.map(_.getEffect).foreach {
      case _: HydrogenWaterEffect =>
        event.getEntityLiving.killWithCause(SuffocationDamageSource())
      case _: OxygenWaterEffect =>
        event.getEntityLiving
          .setAirSupply(event.getEntityLiving.getMaxAirSupply)
      case _ =>
    }

    event.getEntityLiving.getItemBySlot(EquipmentSlot.HEAD).getItem match {
      case _: HydrogenHelmetArmorItem =>
        event.getEntityLiving.hurt(SuffocationDamageSource(), 0.5f)
      case _: OxygenHelmetArmorItem =>
        event.getEntityLiving.setAirSupply(
          event.getEntityLiving.getMaxAirSupply
        )
      case _ =>
    }
  }
}

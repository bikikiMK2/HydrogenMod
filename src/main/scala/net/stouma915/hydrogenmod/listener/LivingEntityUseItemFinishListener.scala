package net.stouma915.hydrogenmod.listener

import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.damagesource.{
  HydrogenDamageSource,
  OxygenDamageSource
}
import net.stouma915.hydrogenmod.item.{HydrogenItem, OxygenItem}

class LivingEntityUseItemFinishListener {
  @SubscribeEvent
  def onLivingEntityUseItemFinish(
      event: LivingEntityUseItemEvent.Finish
  ): Unit =
    event.getItem.getItem match {
      case HydrogenItem.item =>
        event.getEntityLiving.hurt(
          HydrogenDamageSource.damageSource,
          event.getEntityLiving.getHealth
        )
      case OxygenItem.item =>
        event.getEntityLiving.hurt(
          OxygenDamageSource.damageSource,
          event.getEntityLiving.getHealth
        )
      case _ =>
    }
}

package net.stouma915.hydrogenmod.listener

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.damagesource.SuffocationDamageSource
import net.stouma915.hydrogenmod.potion.effect.{
  HydrogenWaterEffect,
  OxygenWaterEffect
}

class LivingUpdateListener {
  @SubscribeEvent
  def onLivingUpdate(event: LivingUpdateEvent): Unit = {
    if (event.getEntityLiving.hasEffect(HydrogenWaterEffect.effect))
      event.getEntityLiving.hurt(
        SuffocationDamageSource.damageSource,
        event.getEntityLiving.getMaxHealth
      )
    if (event.getEntityLiving.hasEffect(OxygenWaterEffect.effect)) {
      event.getEntityLiving.heal(0.1f)
      event.getEntityLiving.setAirSupply(event.getEntityLiving.getMaxAirSupply)
    }
  }
}

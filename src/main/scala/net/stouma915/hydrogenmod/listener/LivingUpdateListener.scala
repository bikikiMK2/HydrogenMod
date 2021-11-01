package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.potion.effect.{
  HydrogenWaterEffect,
  OxygenWaterEffect
}

class LivingUpdateListener {
  @SubscribeEvent
  def onLivingUpdate(event: LivingUpdateEvent): Unit = {
    if (event.getEntityLiving.hasEffect(HydrogenWaterEffect.effect)) {
      event.getEntityLiving.heal(0.5f)

      event.getEntityLiving match {
        case player: Player if player.getFoodData.getFoodLevel < 20 =>
          player.getFoodData.setFoodLevel(player.getFoodData.getFoodLevel + 1)
        case _ =>
      }
    }
    if (event.getEntityLiving.hasEffect(OxygenWaterEffect.effect)) {
      event.getEntityLiving.heal(0.1f)
      event.getEntityLiving.setAirSupply(event.getEntityLiving.getMaxAirSupply)
    }
  }
}

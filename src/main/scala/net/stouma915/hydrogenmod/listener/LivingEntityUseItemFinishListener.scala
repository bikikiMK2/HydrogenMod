package net.stouma915.hydrogenmod.listener

import net.minecraft.world.item.ItemStack
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
    if (event.getItem.sameItem(new ItemStack(HydrogenItem.item)))
      event.getEntityLiving.hurt(
        HydrogenDamageSource.damageSource,
        event.getEntityLiving.getHealth
      )
    else if (event.getItem.sameItem(new ItemStack(OxygenItem.item)))
      event.getEntityLiving.hurt(
        OxygenDamageSource.damageSource,
        event.getEntityLiving.getHealth
      )
}

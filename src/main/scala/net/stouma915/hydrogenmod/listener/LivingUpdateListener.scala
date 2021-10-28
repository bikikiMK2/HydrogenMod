package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.potion.effect.HydrogenWaterEffect

class LivingUpdateListener {
  @SubscribeEvent
  def onLivingUpdate(event: LivingUpdateEvent): Unit =
    if (event.getEntityLiving.hasEffect(HydrogenWaterEffect.effect)) {
      event.getEntityLiving.heal(0.5f)

      // TODO: match構文に置き換える。
      // matchを使おうとするとMinecraftがフリーズする。(java.lang.NoClassDefFoundError: scala/runtime/BoxedUnit)
      // if (event.getEntityLiving.isInstanceOf[Player]) {
      //   val player = event.getEntityLiving.asInstanceOf[Player]
      //   ...
      // }
      // 上記の状態ではIntelliJ IDEAからmatchを使用せよと指摘を受けるため、returnを使用して回避する。
      if (!event.getEntityLiving.isInstanceOf[Player]) return

      val player = event.getEntityLiving.asInstanceOf[Player]
      if (player.getFoodData.getFoodLevel < 20)
        player.getFoodData.setFoodLevel(player.getFoodData.getFoodLevel + 1)
    }
}

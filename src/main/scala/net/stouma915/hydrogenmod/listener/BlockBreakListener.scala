package net.stouma915.hydrogenmod.listener

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.level.block.FireBlock
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.armor.item.{
  HydrogenBootsArmorItem,
  HydrogenChestplateArmorItem,
  HydrogenHelmetArmorItem,
  HydrogenLeggingsArmorItem
}
import net.stouma915.hydrogenmod.implicits._
import net.stouma915.hydrogenmod.item.HydrogenItem
import net.stouma915.hydrogenmod.tool.item.{
  HydrogenShovelItem,
  HydrogenSwordItem
}
import net.stouma915.hydrogenmod.util.Util

class BlockBreakListener {
  @SubscribeEvent
  def onBlockBreak(event: BlockEvent.BreakEvent): Unit =
    event.getState.getBlock match {
      case _: FireBlock =>
        val itemInMainHand =
          event.getPlayer.getItemBySlot(EquipmentSlot.MAINHAND)

        if (
          Seq(
            HydrogenItem(),
            HydrogenBootsArmorItem(),
            HydrogenChestplateArmorItem(),
            HydrogenHelmetArmorItem(),
            HydrogenLeggingsArmorItem(),
            HydrogenSwordItem(),
            HydrogenShovelItem()
          ).contains(itemInMainHand.getItem)
        ) {
          if (!event.getPlayer.isCreative) {
            if (itemInMainHand.sameItem(HydrogenItem().asItemStack))
              itemInMainHand.setCount(itemInMainHand.getCount - 1)
            else
              itemInMainHand.destroyItem(event.getPlayer)
          }
          Util.performHydrogenExplosion(
            event.getPlayer.level,
            event.getPlayer.getPos
          )
        }
      case _ =>
    }
}

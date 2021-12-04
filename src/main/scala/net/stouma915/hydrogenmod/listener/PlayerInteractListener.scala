package net.stouma915.hydrogenmod.listener

import net.minecraft.world.level.block.FireBlock
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.armor.item.{
  HydrogenBootsArmorItem,
  HydrogenChestplateArmorItem,
  HydrogenHelmetArmorItem,
  HydrogenLeggingsArmorItem
}
import net.stouma915.hydrogenmod.implicits.*
import net.stouma915.hydrogenmod.item.{BatteryItem, HydrogenItem}
import net.stouma915.hydrogenmod.tool.item.*
import net.stouma915.hydrogenmod.util.Util

class PlayerInteractListener {

  @SubscribeEvent
  def onPlayerRightClickBlock(
      event: PlayerInteractEvent.RightClickBlock
  ): Unit = {
    val block = event.getWorld.getBlockState(event.getPos).getBlock
    val itemInMainHand = event.getPlayer.getInventory.getSelected

    block match {
      case _: FireBlock =>
        if (
          Seq(
            HydrogenItem(),
            HydrogenBootsArmorItem(),
            HydrogenChestplateArmorItem(),
            HydrogenHelmetArmorItem(),
            HydrogenLeggingsArmorItem(),
            HydrogenSwordItem(),
            HydrogenShovelItem(),
            HydrogenPickaxeItem(),
            HydrogenAxeItem(),
            HydrogenHoeItem()
          ).contains(itemInMainHand.getItem)
        ) {
          if (!event.getPlayer.isCreative) {
            if (itemInMainHand.getItem.canDestroy)
              itemInMainHand.destroyItem(event.getEntityLiving)
            else
              itemInMainHand.setCount(itemInMainHand.getCount - 1)
          }
          Util.performHydrogenExplosion(event.getWorld, event.getPos)
        }

        if (itemInMainHand.getItem == BatteryItem()) {
          if (!event.getPlayer.isCreative)
            itemInMainHand.destroyItem(event.getEntityLiving)
          Util.performBatteryExplosion(event.getWorld, event.getPos)
        }
      case _ =>
    }
  }

}

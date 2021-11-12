package net.stouma915.hydrogenmod.listener

import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.entity.player.PlayerInteractEvent
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
  HydrogenAxeItem,
  HydrogenPickaxeItem,
  HydrogenShovelItem,
  HydrogenSwordItem
}
import net.stouma915.hydrogenmod.util.Util

class PlayerInteractListener {
  @SubscribeEvent
  def onPlayerRightClickBlock(
      event: PlayerInteractEvent.RightClickBlock
  ): Unit = {
    val block = event.getWorld.getBlockState(event.getPos).getBlock
    val itemInMainHand = event.getPlayer.getInventory.getSelected

    if (
      block == Blocks.FIRE &&
      Seq(
        HydrogenItem(),
        HydrogenBootsArmorItem(),
        HydrogenChestplateArmorItem(),
        HydrogenHelmetArmorItem(),
        HydrogenLeggingsArmorItem(),
        HydrogenSwordItem(),
        HydrogenShovelItem(),
        HydrogenPickaxeItem(),
        HydrogenAxeItem()
      ).contains(itemInMainHand.getItem)
    ) {
      if (!event.getPlayer.isCreative) {
        if (itemInMainHand.sameItem(HydrogenItem().asItemStack))
          itemInMainHand.setCount(itemInMainHand.getCount - 1)
        else
          itemInMainHand.destroyItem(event.getEntityLiving)
      }
      Util.performHydrogenExplosion(event.getWorld, event.getPos)
    }
  }
}

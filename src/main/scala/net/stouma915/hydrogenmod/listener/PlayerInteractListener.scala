package net.stouma915.hydrogenmod.listener

import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.implicits._
import net.stouma915.hydrogenmod.item.HydrogenItem
import net.stouma915.hydrogenmod.util.Util

class PlayerInteractListener {
  @SubscribeEvent
  def onPlayerRightClickBlock(
      event: PlayerInteractEvent.RightClickBlock
  ): Unit = {
    val block = event.getWorld.getBlockState(event.getPos).getBlock
    val itemInMainHand = event.getPlayer.getInventory.getSelected
    if (
      itemInMainHand.sameItem(
        HydrogenItem().asItemStack
      ) && block == Blocks.FIRE
    ) {
      if (!event.getPlayer.isCreative)
        itemInMainHand.setCount(itemInMainHand.getCount - 1)
      Util.performHydrogenExplosion(event.getWorld, event.getPos)
    }
  }
}

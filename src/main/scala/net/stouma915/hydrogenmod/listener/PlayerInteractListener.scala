package net.stouma915.hydrogenmod.listener

import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.stouma915.hydrogenmod.item.HydrogenItem

class PlayerInteractListener {
  @SubscribeEvent
  def onPlayerRightClickBlock(
      event: PlayerInteractEvent.RightClickBlock
  ): Unit = {
    val block = event.getWorld.getBlockState(event.getPos).getBlock
    val itemInMainHand = event.getPlayer.getInventory.getSelected
    if (
      itemInMainHand.sameItem(
        new ItemStack(HydrogenItem())
      ) && block == Blocks.FIRE
    ) {
      if (!event.getPlayer.isCreative)
        itemInMainHand.setCount(itemInMainHand.getCount - 1)
      event.getWorld.explode(
        event.getEntity,
        event.getPos.getX,
        event.getPos.getY,
        event.getPos.getZ,
        10.0f,
        Explosion.BlockInteraction.BREAK
      )
    }
  }
}

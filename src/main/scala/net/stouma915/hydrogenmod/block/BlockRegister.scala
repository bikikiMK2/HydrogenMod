package net.stouma915.hydrogenmod.block

import net.minecraft.world.level.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.MODID,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object BlockRegister {
  @SubscribeEvent
  def registerBlocks(event: RegistryEvent.Register[Block]): Unit =
    IndexedSeq(
      ElectrolyzerBlock.instance
    ).foreach(event.getRegistry.register)
}

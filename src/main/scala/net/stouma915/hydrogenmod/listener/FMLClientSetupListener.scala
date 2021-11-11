package net.stouma915.hydrogenmod.listener

import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.block.ElectrolyzerBlock

@Mod.EventBusSubscriber(
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object FMLClientSetupListener {
  @SubscribeEvent
  def onFMLClientSetup(event: FMLClientSetupEvent): Unit =
    event.enqueueWork((() => {
      ItemBlockRenderTypes
        .setRenderLayer(ElectrolyzerBlock(), RenderType.cutout())
    }): Runnable)
}

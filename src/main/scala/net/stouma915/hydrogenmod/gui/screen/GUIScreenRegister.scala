package net.stouma915.hydrogenmod.gui.screen

import net.minecraft.client.gui.screens.MenuScreens
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.gui.menu.ElectrolyzerMenu

@Mod.EventBusSubscriber(
  Array[Dist](Dist.CLIENT),
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object GUIScreenRegister {

  @SubscribeEvent
  def registerGUIScreens(event: FMLClientSetupEvent): Unit =
    event.enqueueWork((() => {
      MenuScreens.register(
        ElectrolyzerMenu(),
        new ElectrolyzerScreen(_, _, _)
      )
    }): Runnable)

}

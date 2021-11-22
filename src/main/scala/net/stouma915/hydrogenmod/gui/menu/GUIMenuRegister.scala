package net.stouma915.hydrogenmod.gui.menu

import net.minecraft.world.inventory.MenuType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object GUIMenuRegister {
  private val guiMenusToRegister = List(
    ElectrolyzerMenu()
  )

  @SubscribeEvent
  def registerGUIMenus(event: RegistryEvent.Register[MenuType[_]]): Unit =
    guiMenusToRegister.foreach(event.getRegistry.register)
}

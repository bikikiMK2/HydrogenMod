package net.stouma915.hydrogenmod.item

import net.minecraft.world.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.item._
import net.stouma915.hydrogenmod.block.item._
import net.stouma915.hydrogenmod.tool.item._

@Mod.EventBusSubscriber(
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object ItemRegister {
  @SubscribeEvent
  def registerItems(
      event: RegistryEvent.Register[Item]
  ): Unit =
    IndexedSeq(
      HydrogenItem(),
      OxygenItem(),
      MetalRodItem(),
      ElectrolyzerBlockItem(),
      HydrogenHelmetArmorItem(),
      HydrogenChestplateArmorItem(),
      HydrogenLeggingsArmorItem(),
      HydrogenBootsArmorItem(),
      OxygenHelmetArmorItem(),
      OxygenChestplateArmorItem(),
      OxygenLeggingsArmorItem(),
      OxygenBootsArmorItem(),
      HydrogenSwordItem(),
      HydrogenShovelItem(),
      HydrogenPickaxeItem()
    ).foreach(event.getRegistry.register)
}

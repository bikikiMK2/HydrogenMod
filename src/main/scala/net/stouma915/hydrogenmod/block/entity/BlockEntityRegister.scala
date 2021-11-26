package net.stouma915.hydrogenmod.block.entity

import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.stouma915.hydrogenmod.HydrogenMod

@Mod.EventBusSubscriber(
  modid = HydrogenMod.ModId,
  bus = Mod.EventBusSubscriber.Bus.MOD
)
object BlockEntityRegister {
  private val blockEntitiesToRegister = List(
    ElectrolyzerBlockEntity()
  )

  @SubscribeEvent
  def registerBlockEntities(
      event: RegistryEvent.Register[BlockEntityType[_]]
  ): Unit =
    blockEntitiesToRegister.foreach(event.getRegistry.register)
}

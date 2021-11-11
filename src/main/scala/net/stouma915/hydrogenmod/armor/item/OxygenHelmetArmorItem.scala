package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{ArmorItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.OxygenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenHelmetArmorItem {
  private val instance: Item = new OxygenHelmetArmorItem()
    .setRegistryName(HydrogenMod.ModId, "oxygen_helmet")

  def apply(): Item = instance
}

sealed class OxygenHelmetArmorItem private ()
    extends ArmorItem(
      OxygenArmorMaterial(),
      EquipmentSlot.HEAD,
      new Properties().tab(HydrogenModTab())
    )

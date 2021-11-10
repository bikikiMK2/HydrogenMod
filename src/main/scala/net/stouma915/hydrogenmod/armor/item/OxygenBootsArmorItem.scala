package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{ArmorItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.OxygenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenBootsArmorItem {
  private val instance: Item = new OxygenBootsArmorItem()
    .setRegistryName(HydrogenMod.MODID, "oxygen_boots")

  def apply(): Item = instance
}

sealed class OxygenBootsArmorItem private ()
    extends ArmorItem(
      OxygenArmorMaterial(),
      EquipmentSlot.FEET,
      new Properties().tab(HydrogenModTab())
    )

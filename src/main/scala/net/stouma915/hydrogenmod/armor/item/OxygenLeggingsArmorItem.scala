package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.{ArmorItem, Item}
import net.minecraft.world.item.Item.Properties
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.OxygenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenLeggingsArmorItem {

  private val instance: Item = new OxygenLeggingsArmorItem()
    .setRegistryName(HydrogenMod.ModId, "oxygen_leggings")

  def apply(): Item = instance

}

sealed class OxygenLeggingsArmorItem private ()
    extends ArmorItem(
      OxygenArmorMaterial(),
      EquipmentSlot.LEGS,
      new Properties().tab(HydrogenModTab())
    )

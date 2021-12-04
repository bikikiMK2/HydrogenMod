package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{ArmorItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.OxygenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object OxygenChestplateArmorItem {

  private val instance: Item = new OxygenChestplateArmorItem()
    .setRegistryName(HydrogenMod.ModId, "oxygen_chestplate")

  def apply(): Item = instance

}

sealed class OxygenChestplateArmorItem private ()
    extends ArmorItem(
      OxygenArmorMaterial(),
      EquipmentSlot.CHEST,
      new Properties().tab(HydrogenModTab())
    )

package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{ArmorItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.HydrogenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object HydrogenHelmetArmorItem {
  private val instance: Item = new HydrogenHelmetArmorItem()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_helmet")

  def apply(): Item = instance
}

sealed class HydrogenHelmetArmorItem private ()
    extends ArmorItem(
      HydrogenArmorMaterial(),
      EquipmentSlot.HEAD,
      new Properties().tab(HydrogenModTab())
    )

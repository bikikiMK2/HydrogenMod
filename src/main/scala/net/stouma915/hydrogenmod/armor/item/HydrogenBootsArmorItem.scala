package net.stouma915.hydrogenmod.armor.item

import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.{ArmorItem, Item}
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.armor.material.HydrogenArmorMaterial
import net.stouma915.hydrogenmod.creativetab.HydrogenModTab

object HydrogenBootsArmorItem {

  private val instance: Item = new HydrogenBootsArmorItem()
    .setRegistryName(HydrogenMod.ModId, "hydrogen_boots")

  def apply(): Item = instance

}

sealed class HydrogenBootsArmorItem private ()
    extends ArmorItem(
      HydrogenArmorMaterial(),
      EquipmentSlot.FEET,
      new Properties().tab(HydrogenModTab())
    )

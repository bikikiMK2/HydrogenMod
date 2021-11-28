package net.stouma915.hydrogenmod.armor.material

import net.minecraft.sounds.{SoundEvent, SoundEvents}
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import net.stouma915.hydrogenmod.HydrogenMod
import net.stouma915.hydrogenmod.item.HydrogenItem

object HydrogenArmorMaterial {

  private val instance: ArmorMaterial = new HydrogenArmorMaterial

  def apply(): ArmorMaterial = instance

}

sealed class HydrogenArmorMaterial private () extends ArmorMaterial {

  override def getDurabilityForSlot(p_40410_ : EquipmentSlot): Int =
    Int.MaxValue

  override def getDefenseForSlot(p_40411_ : EquipmentSlot): Int = 0

  override def getEnchantmentValue: Int = 0

  override def getEquipSound: SoundEvent = SoundEvents.ARMOR_EQUIP_LEATHER

  override def getRepairIngredient: Ingredient = Ingredient.of(HydrogenItem())

  override def getName: String = s"${HydrogenMod.ModId}:hydrogen"

  override def getToughness: Float = 0.0f

  override def getKnockbackResistance: Float = 0.0f

}

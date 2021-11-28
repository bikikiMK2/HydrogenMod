package net.stouma915.hydrogenmod.tool.tier

import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import net.stouma915.hydrogenmod.item.HydrogenItem

object HydrogenTier {

  private val instance: Tier = new HydrogenTier

  def apply(): Tier = instance

}

sealed class HydrogenTier private () extends Tier {

  override def getUses: Int = 50

  override def getSpeed: Float = 0.0f

  override def getAttackDamageBonus: Float = 0.0f

  override def getEnchantmentValue: Int = 0

  override def getRepairIngredient: Ingredient =
    Ingredient.of(HydrogenItem())

  override def getLevel: Int = 0

}

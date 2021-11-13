package net.stouma915.hydrogenmod.tool.tier

import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient
import net.stouma915.hydrogenmod.item.OxygenItem

object OxygenTier {
  private val instance: Tier = new OxygenTier

  def apply(): Tier = instance
}

sealed class OxygenTier private () extends Tier {
  override def getUses: Int = 50

  override def getSpeed: Float = 0.0f

  override def getAttackDamageBonus: Float = 0.0f

  override def getEnchantmentValue: Int = 0

  override def getRepairIngredient: Ingredient =
    Ingredient.of(OxygenItem())

  @SuppressWarnings(Array("deprecation"))
  override def getLevel: Int = 0
}

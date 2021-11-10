package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object OxygenDamageSource {
  private val instance: DamageSource = new OxygenDamageSource

  def apply(): DamageSource = instance
}

sealed class OxygenDamageSource private ()
    extends DamageSource(s"${HydrogenMod.MODID}_ate_oxygen") {
  override def isBypassArmor: Boolean = true

  override def isDamageHelmet: Boolean = false

  override def isFall: Boolean = false
}

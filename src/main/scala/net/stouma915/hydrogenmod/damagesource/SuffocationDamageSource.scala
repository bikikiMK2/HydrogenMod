package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object SuffocationDamageSource {
  private val instance: SuffocationDamageSource = new SuffocationDamageSource

  def apply(): DamageSource = instance
}

sealed class SuffocationDamageSource private ()
    extends DamageSource(s"${HydrogenMod.ModId}_lack_of_oxygen") {
  override def isBypassArmor: Boolean = true

  override def isDamageHelmet: Boolean = false

  override def isFall: Boolean = false
}

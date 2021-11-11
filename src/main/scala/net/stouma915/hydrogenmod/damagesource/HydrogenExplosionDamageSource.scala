package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenExplosionDamageSource {
  private val instance: DamageSource = new HydrogenExplosionDamageSource

  def apply(): DamageSource = instance
}

sealed class HydrogenExplosionDamageSource private ()
    extends DamageSource(s"${HydrogenMod.ModId}_hydrogen_explosion") {
  override def isBypassArmor: Boolean = true

  override def isDamageHelmet: Boolean = false

  override def isFall: Boolean = false
}

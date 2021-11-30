package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object BatteryExplosionDamageSource {

  private val instance: DamageSource = new BatteryExplosionDamageSource

  def apply(): DamageSource = instance

}

sealed class BatteryExplosionDamageSource private ()
    extends DamageSource(s"${HydrogenMod.ModId}_battery_explosion") {

  override def isBypassArmor: Boolean = true

  override def isDamageHelmet: Boolean = false

  override def isFall: Boolean = false

}

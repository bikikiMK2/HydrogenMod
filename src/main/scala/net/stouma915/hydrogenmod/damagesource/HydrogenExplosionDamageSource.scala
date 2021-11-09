package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenExplosionDamageSource {
  private val instance: DamageSource = new HydrogenExplosionDamageSource

  def apply(): DamageSource = instance
}

sealed class HydrogenExplosionDamageSource private ()
    extends DamageSource(s"${HydrogenMod.MODID}_hydrogen_explosion")

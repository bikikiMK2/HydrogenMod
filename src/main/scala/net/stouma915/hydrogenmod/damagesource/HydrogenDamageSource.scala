package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenDamageSource {
  private val instance: DamageSource = new HydrogenDamageSource

  def apply(): DamageSource = instance
}

sealed class HydrogenDamageSource private ()
    extends DamageSource(s"${HydrogenMod.MODID}_ate_hydrogen")

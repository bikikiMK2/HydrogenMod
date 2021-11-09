package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object SuffocationDamageSource {
  private val instance: SuffocationDamageSource = new SuffocationDamageSource

  def apply(): DamageSource = instance
}

sealed class SuffocationDamageSource private ()
    extends DamageSource(s"${HydrogenMod.MODID}_lack_of_oxygen")

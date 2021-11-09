package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object SuffocationDamageSource {
  val instance: SuffocationDamageSource = new SuffocationDamageSource
}

sealed class SuffocationDamageSource private ()
    extends DamageSource(s"${HydrogenMod.MODID}_lack_of_oxygen")

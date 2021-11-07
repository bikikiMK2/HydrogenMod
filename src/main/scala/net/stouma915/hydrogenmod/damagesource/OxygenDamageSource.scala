package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object OxygenDamageSource {
  val damageSource: DamageSource = new OxygenDamageSource
}

class OxygenDamageSource
    extends DamageSource(s"${HydrogenMod.MODID}_ate_oxygen")

package net.stouma915.hydrogenmod.damagesource

import net.minecraft.world.damagesource.DamageSource
import net.stouma915.hydrogenmod.HydrogenMod

object HydrogenDamageSource {
  val instance: DamageSource = new HydrogenDamageSource
}

class HydrogenDamageSource
    extends DamageSource(s"${HydrogenMod.MODID}_ate_hydrogen")

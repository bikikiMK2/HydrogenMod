package net.stouma915.hydrogenmod.util

import net.minecraft.core.BlockPos
import net.minecraft.world.level.{Explosion, ExplosionDamageCalculator, Level}
import net.stouma915.hydrogenmod.damagesource.HydrogenExplosionDamageSource

object Util {
  def performHydrogenExplosion(level: Level, blockPos: BlockPos): Unit =
    level.explode(
      null,
      HydrogenExplosionDamageSource(),
      new ExplosionDamageCalculator,
      blockPos.getX,
      blockPos.getY,
      blockPos.getZ,
      10.0f,
      false,
      Explosion.BlockInteraction.BREAK
    )
}

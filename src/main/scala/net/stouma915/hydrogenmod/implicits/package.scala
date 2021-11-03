package net.stouma915.hydrogenmod

package object implicits {
  implicit class AnyRefOps(anyRef: AnyRef) {
    def isNull: Boolean = anyRef == null

    def nonNull: Boolean = !isNull
  }
}

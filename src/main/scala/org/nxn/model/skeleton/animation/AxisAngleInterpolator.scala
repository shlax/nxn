package org.nxn.model.skeleton.animation

import org.nxn.model.skeleton.AxisAngle

class AxisAngleInterpolator(val angle:AxisAngle) {
  val interpolator = Interpolator()

  def update(value: Float, nextValue:Float):Unit = {
    val to = if (java.lang.Float.isNaN(value)) interpolator.s else value
    val next = if (java.lang.Float.isNaN(nextValue)) to else nextValue

    interpolator.update(to, next)
  }

}

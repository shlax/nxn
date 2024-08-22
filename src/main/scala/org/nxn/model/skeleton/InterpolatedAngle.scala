package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Axis, Matrix4f}

class InterpolatedAngle(val angle: Angle3f, val to:Axis, val value:Float){

  def rotation(): Matrix4f = {
    val v = value * angle()
    to.rotate(v)
  }

  def rotation(m:Matrix4f):Matrix4f = {
    val v = value * angle()
    to.rotate(m, v)
  }

}

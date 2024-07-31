package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Matrix4f}
import org.nxn.utils.Axis

class InterpolatedAngle(val angle: Angle3f, val to:Axis, val value:Float){

  def apply(m:Matrix4f):Matrix4f = {
    val v = value * angle()
    to match {
      case Axis.X => m.xRot(v)
      case Axis.Y => m.yRot(v)
      case Axis.Z => m.zRot(v)
    }
  }

}

package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Axis, Matrix4f}

class AxisAngle(val axis:Axis){

  val angle:Angle3f = Angle3f()

  def rotation(): Matrix4f = {
    axis.rotate(angle())
  }

  def rotation(m:Matrix4f): Matrix4f = {
    axis.rotate(m, angle())
  }

}

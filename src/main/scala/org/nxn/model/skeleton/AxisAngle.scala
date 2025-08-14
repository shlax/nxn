package org.nxn.model.skeleton

import org.nxn.math.{Axis, Matrix4f}

class AxisAngle(val axis:Axis){

  var angle:Float = 0f

  def rotation(): Matrix4f = {
    axis.rotate(angle)
  }

  def rotation(m:Matrix4f): Matrix4f = {
    axis.rotate(m, angle)
  }

}

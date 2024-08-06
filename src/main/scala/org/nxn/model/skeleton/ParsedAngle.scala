package org.nxn.model.skeleton

import org.nxn.utils.Axis

class ParsedAngle(val from:Axis, val to:Axis, val value:Float){

  def apply(parent:RotatingJoint): InterpolatedAngle = {
    InterpolatedAngle(parent.angle(from), to, value)
  }

}

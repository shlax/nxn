package org.nxn.model.skeleton.animation

import org.nxn.math.Axis
import org.nxn.model.skeleton.RotatingJoint

class ParsedJointAngle(val axis:Axis, val value:Float) {

  def apply(j: RotatingJoint):KeyFrameAngle = {
    val a = j.angle(axis)
    KeyFrameAngle(a, value)
  }

}

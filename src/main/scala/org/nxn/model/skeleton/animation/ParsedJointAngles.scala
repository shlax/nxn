package org.nxn.model.skeleton.animation

import org.nxn.model.skeleton.{AbstractJoint, RotatingJoint}

class ParsedJointAngles(val name:String, val angles:Array[ParsedJointAngle]) {

  def apply(j:AbstractJoint): Array[KeyFrameAngle] = {
    j.apply(name).get match {
      case rj : RotatingJoint =>
        angles.map(a => a(rj))
    }
  }

}

package org.nxn.model.skeleton.animation

import org.nxn.math.Axis

class ParsedJointAngles(val name:String, val angles:Array[ParsedJointAngle]) {

  def apply(a:Axis):Option[ParsedJointAngle] = {
    var res:Option[ParsedJointAngle] = None
    for(a <- angles if a.axis == a){
      res = Some(a)
    }
    res
  }

}

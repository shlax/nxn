package org.nxn.model.skeleton.animation

import org.nxn.model.skeleton.AbstractJoint

import scala.collection.mutable

class ParsedKeyFrame(jointsAngles: Array[ParsedJointAngles]) {

  def apply(j:AbstractJoint): KeyFrame = {
    val l = new mutable.ArrayBuffer[KeyFrameAngle]()
    for(a <- jointsAngles) l ++= a(j)
    KeyFrame(l.toArray)
  }

}

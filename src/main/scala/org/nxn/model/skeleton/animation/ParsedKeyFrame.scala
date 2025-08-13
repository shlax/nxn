package org.nxn.model.skeleton.animation

import org.nxn.model.skeleton.{AbstractJoint, RotatingJoint}

import scala.collection.mutable

class ParsedKeyFrame(jointsAngles: Array[ParsedJointAngles]) {

  def apply(j:AbstractJoint): KeyFrame = {
    val angles = mutable.ArrayBuffer[Float]()

    j.traverse{
      case r : RotatingJoint =>
        val ang = jointsAngles.find(_.name == r.name).map(_.angles)
        for(a <- r.angles){
          var v: Float = Float.NaN
          for(pa <- ang){
            val ov = pa.find(_.axis == a.axis).map(a => a.value)
            for(x <- ov) v = x
          }
          angles += v
        }
    }

    KeyFrame(angles.toArray)
  }

}

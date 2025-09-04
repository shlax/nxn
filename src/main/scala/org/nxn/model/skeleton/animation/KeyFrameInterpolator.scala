package org.nxn.model.skeleton.animation

import org.nxn.model.skeleton.{AbstractJoint, RotatingJoint}

import scala.collection.mutable

class KeyFrameInterpolator(j:AbstractJoint) {

  val interpolators:Array[Interpolator] = {
    val i = mutable.ArrayBuffer[Interpolator]()
    j.traverse {
      case r: RotatingJoint =>
        for (a <- r.angles) {
          i += Interpolator()
        }
    }
    i.toArray
  }

  def update(to:KeyFrame, next:KeyFrame): Unit = {
    for(i <- interpolators.indices){
      val intr = interpolators(i)

      val toAngle = to(i)
      val nextAngle = next(i)

      val toValue = if( java.lang.Float.isNaN(toAngle) ) intr.s else toAngle
      val nextValue = if( java.lang.Float.isNaN(nextAngle) ) toValue else nextAngle

      intr.update(toValue, nextValue)
    }
  }

}

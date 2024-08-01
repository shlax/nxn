package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Matrix4f, Vector3f}
import org.nxn.utils.Axis

class RotatingJoint(name:String, val point: Vector3f, val angles:Array[AxisAngle],
                      vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(name, vertexes, subJoints){

  def angle(a:Axis): Angle3f = {
    angles.find(_.axis == a).get.angle
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    if(angles.length == 1){
      val rot = angles.head.rotation()

      val tmp = new Matrix4f(point).mulMxT(rot).mulMxT(modelMatrix)
      rot.mulMxT(normalMatrix)

      update(tmp, rot)
    }else{
      val tmp = angles.head.rotation()
      val rot = angles.tail.head.rotation().mulMxT(tmp)
      for (i <- angles.tail.tail) rot.mulTxM(i.rotation(tmp))

      tmp.set(point).mulMxT(rot).mulMxT(modelMatrix)
      rot.mulMxT(normalMatrix)

      update(tmp, rot)
    }
  }

}

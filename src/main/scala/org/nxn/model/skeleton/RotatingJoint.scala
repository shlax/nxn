package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Axis, Matrix4f, Vector3f}

class RotatingJoint(name:String, val point: Vector3f, val angles:Array[AxisAngle],
                      vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(name, vertexes, subJoints){

  def angle(a:Axis): Angle3f = {
    angles.find(_.axis == a).get.angle
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    if(angles.length == 1){
      val rot = angles.head.rotation()

      val tmp = Matrix4f(point).mulWithThis(rot).mulWithThis(modelMatrix)
      rot.mulWithThis(normalMatrix)

      update(tmp, rot)
    }else{
      val tmp = angles.head.rotation()
      val rot = angles.tail.head.rotation().mulWithThis(tmp)
      for (i <- angles.tail.tail) rot.mulThisWith(i.rotation(tmp))

      tmp.set(point).mulWithThis(rot).mulWithThis(modelMatrix)
      rot.mulWithThis(normalMatrix)

      update(tmp, rot)
    }
  }

}

package org.nxn.model.skeleton

import org.nxn.math.{Angle3f, Axis, Matrix4f, Vector3f}

class RotatingJoint(name:String, val point: Vector3f, val angles:Array[AxisAngle],
                      vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(name, vertexes, subJoints){

  def angle(a:Axis): AxisAngle = {
    angles.find(_.axis == a).get
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    if(angles.length == 1){
      val rot = angles.head.rotation()

      val tmp = Matrix4f(point).mulWith(rot).mulWith(modelMatrix)
      rot.mulWith(normalMatrix)

      update(tmp, rot)
    }else{
      val tmp = angles.head.rotation()
      val rot = angles.tail.head.rotation().mulWith(tmp)
      for (i <- angles.tail.tail) rot.mul(i.rotation(tmp))

      tmp.set(point).mulWith(rot).mulWith(modelMatrix)
      rot.mulWith(normalMatrix)

      update(tmp, rot)
    }
  }

}

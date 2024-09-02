package org.nxn.model.skeleton

import org.nxn.math.Matrix4f

class InterpolatedJoint(name:String, val interpolations:Array[InterpolatedAngle],
                          vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(name, vertexes, subJoints){
  
  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    if(interpolations.length == 1){
      val rot = interpolations.head.rotation()

      val tmp = Matrix4f.mul(modelMatrix, rot)
      rot.mulWithThis(normalMatrix)

      update(tmp, rot)
    }else{
      val tmp = interpolations.head.rotation()
      val rot = interpolations.tail.head.rotation().mulWithThis(tmp)
      for (i <- interpolations.tail.tail) rot.mulThisWith(i.rotation(tmp))

      tmp.mul(modelMatrix, rot)
      rot.mulWithThis(normalMatrix)

      update(tmp, rot)
    }
  }

}

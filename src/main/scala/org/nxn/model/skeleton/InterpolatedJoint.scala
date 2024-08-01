package org.nxn.model.skeleton

import org.nxn.math.Matrix4f

class InterpolatedJoint(val interpolations:Array[InterpolatedAngle],
                          vertexes:Array[SkinVertex], subJoints:Array[AbstractJoint]) extends AbstractJoint(vertexes, subJoints){

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {

    if(interpolations.length == 1){
      val rot = interpolations.head(new Matrix4f()).mulMxT(normalMatrix)

      val tmp = Matrix4f.mul(modelMatrix, rot)

      for (s <- subJoints) s(tmp, rot)
    }else{
      val tmp = interpolations.head(new Matrix4f())
      val rot = interpolations.tail.head(new Matrix4f()).mulMxT(tmp)
      for (i <- interpolations.tail.tail) rot.mulTxM(i(tmp))
      rot.mulMxT(normalMatrix)

      tmp.mul(modelMatrix, rot)

      for (s <- subJoints) s(tmp, rot)
    }

  }

}

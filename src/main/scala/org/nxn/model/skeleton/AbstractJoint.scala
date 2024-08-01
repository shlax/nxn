package org.nxn.model.skeleton

import org.nxn.math.Matrix4f

abstract class AbstractJoint(val name:String, val vertexes:Array[SkinVertex], val subJoints:Array[AbstractJoint]){

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit

  def update(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    for(s <- subJoints) s(modelMatrix, normalMatrix)
    for(v <- vertexes) v(modelMatrix, normalMatrix)
  }

}

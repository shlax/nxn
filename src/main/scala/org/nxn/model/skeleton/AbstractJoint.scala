package org.nxn.model.skeleton

import org.nxn.math.Matrix4f

abstract class AbstractJoint(val name:String, val vertexes:Array[SkinVertex], val subJoints:Array[AbstractJoint]){

  def apply(nm:String): Option[AbstractJoint] = {
    if(nm == name) Some(this)
    else{
      var res:Option[AbstractJoint] = None
      for(a <- subJoints if res.isEmpty) res = a(nm)
      res
    }
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit

  def update(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    for(s <- subJoints) s(modelMatrix, normalMatrix)
    for(v <- vertexes) v(modelMatrix, normalMatrix)
  }

}

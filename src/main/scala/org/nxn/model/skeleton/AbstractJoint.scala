package org.nxn.model.skeleton

import org.nxn.math.Matrix4f

abstract class AbstractJoint(val vertexes:Array[SkinVertex], val subJoints:Array[AbstractJoint]){

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit

}

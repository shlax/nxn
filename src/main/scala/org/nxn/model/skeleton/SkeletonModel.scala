package org.nxn.model.skeleton

import org.nxn.math.Matrix4f
import org.nxn.model.CompiledModel

object SkeletonModel{

  def apply(root:ParsedJoint, models:Map[String, CompiledModel]):SkeletonModel = {
    val joint = root.apply(models)
    val skins = models.map( (k, v) => SkinModel(k, v.vulkanModel)).toArray
    new SkeletonModel(joint, skins)
  }

}

class SkeletonModel(val rootJoint: AbstractJoint, val models:Array[SkinModel]){

  def apply(nm:String) : SkinModel = {
    models.find(_.name == nm).get
  }

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    rootJoint(modelMatrix, normalMatrix)
  }

}

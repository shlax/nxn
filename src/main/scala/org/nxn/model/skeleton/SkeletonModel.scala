package org.nxn.model.skeleton

import org.nxn.model.CompiledModel

object SkeletonModel{

  def apply(root:ParsedJoint, models:Map[String, CompiledModel]):SkeletonModel = {
    val joint = root.apply(models)
    val skins = models.map( (k, v) => (k, SkinModel(k, v.vulkanModel)) )
    new SkeletonModel(joint, skins)
  }

}

class SkeletonModel(val rootJoint: AbstractJoint, val models:Map[String, SkinModel]){

  def apply(nm:String) : SkinModel = {
    models(nm)
  }

}

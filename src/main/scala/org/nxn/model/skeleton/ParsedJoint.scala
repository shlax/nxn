package org.nxn.model.skeleton

import org.nxn.math.Vector3f
import org.nxn.model.IndexedModel

import scala.collection.mutable

class ParsedJoint(val name:String, val point: Vector3f, val angles:Array[ParsedAngle],
                    val binding: Array[ParsedBinding], val subJoints: Array[ParsedJoint]){

  if(point == null && angles == null) throw new IllegalArgumentException("point == null && angles == null")
  if(point != null && angles != null) throw new IllegalArgumentException("point != null && angles != null")
  if(angles != null){
    if(angles.length == 0) throw new IllegalArgumentException("angles == 0")
    if(angles.length > 3) throw new IllegalArgumentException("angles > 3 :" + angles.length)
    if(angles.groupBy(_.to).maxBy(_._2.length)._2.length > 1) throw new IllegalArgumentException("duplicate to angle value")
  }

  def apply(models:Map[String, IndexedModel]):AbstractJoint = {
    val vertexes = new mutable.ArrayBuffer[SkinVertex]()
    if(binding != null) {
      for (b <- binding) {
        val m = models(b.mesh)
        for (i <- b.indexes) {
          vertexes += SkinVertex(m(i))
        }
      }
    }

    val sub = if(subJoints == null) new Array[AbstractJoint](0) else subJoints.map(j => j(models))
    new AbstractJoint(vertexes.toArray, sub)
  }

}

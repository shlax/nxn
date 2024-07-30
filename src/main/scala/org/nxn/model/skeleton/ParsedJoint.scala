package org.nxn.model.skeleton

import org.nxn.math.Vector3f
import org.nxn.model.IndexedModel

import scala.collection.mutable

class ParsedJoint(val name:String, val point: Vector3f, val angles:Array[ParsedAngle],
                    val binding: Array[ParsedBinding], val subJoints: Array[ParsedJoint]){
  if(angles.length > 3) throw new IllegalArgumentException("to many angles "+angles.length)
  if(angles.groupBy(_.to).maxBy(_._2.length)._2.length > 1) throw new IllegalArgumentException("duplicate angle value")

  def apply(models:Map[String, IndexedModel]):VulkanJoint = {
    val vertexes = new mutable.ArrayBuffer[SkinVertex]()
    for(b <- binding){
      val m = models(b.mesh)
      for(i <- b.indexes){
        vertexes += SkinVertex(m(i))
      }
    }
    new VulkanJoint(vertexes.toArray)
  }

}

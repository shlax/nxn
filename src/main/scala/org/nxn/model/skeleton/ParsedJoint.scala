package org.nxn.model.skeleton

import org.nxn.math.Vector3f
import org.nxn.model.IndexedModel

import scala.collection.mutable

class ParsedJoint(val name:String, val vec: Vector3f, val angle:String, val binding: Array[ParsedBinding], val subJoints: Array[ParsedJoint]){

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

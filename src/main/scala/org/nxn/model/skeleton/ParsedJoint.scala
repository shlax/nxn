package org.nxn.model.skeleton

import org.nxn.math.Vector3f
import org.nxn.model.CompiledModel
import org.nxn.utils.Axis

import scala.collection.mutable

class ParsedJoint(val name:String, val point: Vector3f, val axis:Array[Axis], val angles:Array[ParsedAngle],
                    val binding: Array[ParsedBinding], val subJoints: Array[ParsedJoint]){

  if(point == null && angles == null) throw new IllegalArgumentException("point == null && angles == null")
  if(point != null && angles != null) throw new IllegalArgumentException("point != null && angles != null")
  if(point != null && axis == null) throw new IllegalArgumentException("point != null && axis == null")
  if(angles != null){
    if(angles.length == 0) throw new IllegalArgumentException("angles == 0")
    if(angles.length > 3) throw new IllegalArgumentException("angles > 3 :" + angles.length)
    if(angles.groupBy(_.to).maxBy(_._2.length)._2.length > 1) throw new IllegalArgumentException("duplicate to angle value")
  }else if (axis != null) {
    if (axis.length == 0) throw new IllegalArgumentException("axis == 0")
    if (axis.length > 3) throw new IllegalArgumentException("axis > 3 :" + axis.length)
    if (axis.groupBy(_.name()).maxBy(_._2.length)._2.length > 1) throw new IllegalArgumentException("duplicate axis value")
  }

  def apply(parent:RotatingJoint, models:Map[String, CompiledModel]):AbstractJoint = {
    val vertexes = new mutable.ArrayBuffer[SkinVertex]()
    if(binding != null) {
      for (b <- binding) {
        val m = models(b.mesh)
        for (i <- b.indexes) {
          vertexes += SkinVertex(m(i))
        }
      }
    }

    val sub = if(subJoints == null) new Array[AbstractJoint](0) else new Array[AbstractJoint](subJoints.length)

    val ret = if(point != null) new RotatingJoint(name.intern(), point, vertexes.toArray, sub)
      else{
        val ang = angles.map(i => i(parent))
        new InterpolatedJoint(name.intern(), ang, vertexes.toArray, sub)
      }

    val par = ret match {
        case j: RotatingJoint => j
        case _ => parent
      }

    for(i <- subJoints.zipWithIndex) sub(i._2) = i._1(par, models)

    ret
  }

}

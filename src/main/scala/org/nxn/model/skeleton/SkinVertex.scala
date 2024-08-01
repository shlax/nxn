package org.nxn.model.skeleton

import org.nxn.math.{Matrix4f, Vector3f}
import org.nxn.model.Vertex

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object SkinVertex {

  def apply(points:Array[Vertex]):SkinVertex = {
    val point = points.head.point
    val normals = new ArrayBuffer[SkinNormal]()

    for(v <- points){
      if(!point.eq(v.point)) {
        throw new IllegalArgumentException("" + point + " != " + v.point)
      }

      normals += normals.find(_.is(v.normal)).getOrElse(SkinNormal(v.normal))
    }

    new SkinVertex(new Vector3f(point), point, normals.toArray)
  }

}

class SkinVertex(val inPoint:Vector3f, val outPoint:Vector3f, val normals:Array[SkinNormal]) {

  def apply(modelMatrix:Matrix4f, normalMatrix:Matrix4f):Unit = {
    modelMatrix(inPoint, outPoint)
    for(n <- normals) n(normalMatrix)
  }

}

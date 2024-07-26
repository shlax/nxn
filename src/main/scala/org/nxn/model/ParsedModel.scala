package org.nxn.model

import org.nxn.math.{Vector2f, Vector3f}
import scala.collection.mutable

class ParsedModel(val points:Array[Vector3f], val faces:Array[ParsedTriangle]){

  def invert():this.type = {
    for(p <- points) p.y = -1 * p.y
    for(f <- faces) f.invert()
    this
  }

  def compile(epsilon:Float = 0.0000001f):IndexedModel = {
    val vertexes = new Array[mutable.ArrayBuffer[(Int, VulkanVertex)]](points.length)
    val indexes = new Array[VulkanTriangle](faces.length)
    var ind = 0

    def eqVector2f(a: Vector2f, b: Vector2f): Boolean = {
      Math.abs(a.x - b.x) < epsilon && Math.abs(a.y - b.y) < epsilon
    }

    def eqVector3f(a:Vector3f, b:Vector3f):Boolean = {
      Math.abs(a.x - b.x) < epsilon && Math.abs(a.y - b.y) < epsilon && Math.abs(a.z - b.z) < epsilon
    }

    def eqVertex(a:VulkanVertex, b:ParsedVertex):Boolean = {
      eqVector3f(a.point, points(b.index)) && eqVector3f(a.normal, b.normal)
        && a.uvs.zipWithIndex.forall( (uv, i) => eqVector2f(uv, b.uvs(i)) )
    }

    var next = 0
    def put(pv:ParsedVertex):Int = {
      var v = vertexes(pv.index)
      if(v == null){
        v = new mutable.ArrayBuffer[(Int, VulkanVertex)]
        vertexes(pv.index) = v
      }

      val f = v.find( x => eqVertex(x._2, pv) )
      if(f.isDefined) f.get._1 else {
        val act = next
        v.addOne( (act, new VulkanVertex(points(pv.index), pv.normal, pv.uvs)) )
        next += 1
        act
      }
    }

    for(f <- faces){
      indexes(ind) = new VulkanTriangle(put(f.a), put(f.b), put(f.c))
      ind += 1
    }

    val vertexArray = new Array[VulkanVertex](next)
    for(i <- vertexes; j <- i) vertexArray(j._1) = j._2

    val indMap = new Array[Array[Int]](vertexes.length)
    for(i <- vertexes.zipWithIndex) indMap(i._2) = i._1.map(_._1).toArray

    new IndexedModel(new VulkanModel(vertexArray, indexes), indMap)
  }

}

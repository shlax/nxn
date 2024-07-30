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
    class IndexVertex(val index:Int, val vertex:VulkanVertex)

    val vertexes = new Array[mutable.ArrayBuffer[IndexVertex]](points.length)
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
        v = new mutable.ArrayBuffer[IndexVertex]()
        vertexes(pv.index) = v
      }

      v.find( x => eqVertex(x.vertex, pv) ).map(_.index).getOrElse {
        val act = next
        val normal = v.map(_.vertex.normal).find( i => eqVector3f(i, pv.normal) ).getOrElse(pv.normal)
        val uvs = pv.uvs.zipWithIndex.map( i => v.map(_.vertex.uvs(i._2)).find( j => eqVector2f(j, i._1) ).getOrElse(i._1) )
        v += new IndexVertex(act, new VulkanVertex(points(pv.index), normal, uvs))
        next += 1
        act
      }
    }

    for(f <- faces){
      indexes(ind) = new VulkanTriangle(put(f.a), put(f.b), put(f.c))
      ind += 1
    }

    val vertexArray = new Array[VulkanVertex](next)
    for(i <- vertexes; j <- i) vertexArray(j.index) = j.vertex

    ind = 0
    val indMap = new Array[Array[Int]](vertexes.length)
    for(i <- vertexes){
      indMap(ind) = i.map(_.index).toArray
      ind += 1
    }

    new IndexedModel(new VulkanModel(vertexArray, indexes), indMap)
  }

}

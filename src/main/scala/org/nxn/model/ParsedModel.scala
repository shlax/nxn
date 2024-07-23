package org.nxn.model

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.nxn.math.{Vector2f, Vector3f}
import org.nxn.model.parser.{ModelLexer, ModelParser}

import java.io.InputStream
import java.nio.charset.StandardCharsets
import scala.collection.mutable

object ParsedModel{

  def load(in:InputStream): ParsedModel = {
    val p = new ModelParser(new CommonTokenStream(new ModelLexer(CharStreams.fromStream(in, StandardCharsets.UTF_8))))
    p.model().result
  }

}

class ParsedModel(val points:Array[Vector3f], val faces:Array[ParsedTriangle]){

  def toVulkanModel(epsilon:Float = 0.0000001f):IndexedModel = {
    val vertexes = mutable.Map[Int, mutable.ArrayBuffer[(Int, VulkanVertex)]]()
    val indexes = mutable.ArrayBuffer[VulkanTriangle]()

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
      val v = vertexes.getOrElseUpdate(pv.index, new mutable.ArrayBuffer[(Int, VulkanVertex)])
      val f = v.find( x => eqVertex(x._2, pv) )
      if(f.isDefined) f.get._1 else {
        val act = next
        v.addOne( (act, new VulkanVertex(points(pv.index), pv.normal, pv.uvs)) )
        next += 1
        act
      }
    }

    for(f <- faces){
      indexes.addOne(new VulkanTriangle(put(f.a), put(f.b), put(f.c)))
    }

    val vertexArray = new Array[VulkanVertex](next)
    for(i <- vertexes; j <- i._2){
      vertexArray(j._1) = j._2
    }

    val indMap = new Array[Array[Int]](vertexes.size)
    for(i <- vertexes) indMap(i._1) = i._2.map(_._1).toArray

    new IndexedModel(new VulkanModel(vertexArray, indexes.toArray), indMap)
  }

}

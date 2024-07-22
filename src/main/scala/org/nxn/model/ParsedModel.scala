package org.nxn.model

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.nxn.math.Vector3f
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

  def toVulkanModel():VulkanModel = {
    val vertexes = mutable.Map[Int, mutable.ArrayBuffer[(Int, VulkanVertex)]]()
    val indexes = mutable.ArrayBuffer[VulkanTriangle]()



    def eq(a:VulkanVertex, b:ParsedVertex):Boolean = {
      ???
    }

    var next = 0
    def put(pv:ParsedVertex):Int = {
      val v = vertexes.getOrElseUpdate(pv.index, new mutable.ArrayBuffer[(Int, VulkanVertex)])
      val f = v.find( x => eq(x._2, pv) )
      if(f.isDefined) f.get._1 else {
        val act = next
        v.addOne( (act, new VulkanVertex(points(pv.index), pv.normal, pv.uws)) )
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

    new VulkanModel(vertexArray, indexes.toArray)
  }

}

package org.nxn.model

import org.nxn.vulkan.memory.{ToFloatBuffer, ToIntBuffer}

import java.nio.{FloatBuffer, IntBuffer}

class VulkanModel(val vertexes: Array[VulkanVertex], val indexes:Array[VulkanTriangle]) extends ToIntBuffer, ToFloatBuffer{

  override def toFloatBuffer(b: FloatBuffer): FloatBuffer = {
    for(v <- vertexes) v.toFloatBuffer(b)
    b
  }

  override def toIntBuffer(b: IntBuffer): IntBuffer = {
    for(i <- indexes) i.toIntBuffer(b)
    b
  }

  def vertexesSize() : Int = {
    vertexes.length * stride()
  }

  def stride():Int = {
    vertexes.head.size()
  }

  def indexesSize(): Int = {
    3 * indexes.head.size()
  }

}

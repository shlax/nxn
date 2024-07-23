package org.nxn.model

import org.nxn.vulkan.memory.{ToFloatBuffer, ToIntBuffer}

import java.nio.{FloatBuffer, IntBuffer}

class VulkanModel(val vertexes: Array[VulkanVertex], val indexes:Array[VulkanTriangle]) extends ToIntBuffer, ToFloatBuffer{

  override def toFloatBuffer(b: FloatBuffer): FloatBuffer = {
    for(v <- vertexes) v.toFloatBuffer(b)
    b
  }

  def vertexesSize() : Int = {
    vertexes.length * vertexesStride()
  }

  def vertexesStride():Int = {
    vertexes.head.size()
  }

  override def toIntBuffer(b: IntBuffer): IntBuffer = {
    for (i <- indexes) i.toIntBuffer(b)
    b
  }

  def indexesSize(): Int = {
    indexes.length * indexesStride()
  }

  def indexesStride(): Int = {
    indexes.head.size()
  }

  /** see vkCmdDrawIndexed */
  def indexesCount(): Int = {
    indexes.length * 3
  }

}

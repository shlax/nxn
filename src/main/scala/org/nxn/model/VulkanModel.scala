package org.nxn.model

import org.nxn.vulkan.memory.{ToFloatBuffer, ToIntBuffer}

import java.nio.{FloatBuffer, IntBuffer}

class VulkanModel(val vertexes: Array[VulkanVertex], val indexes:Array[VulkanTriangle]) extends ToIntBuffer, ToFloatBuffer{

  override def write(b: FloatBuffer): FloatBuffer = {
    for(v <- vertexes) v.write(b)
    b
  }

  override def write(b: IntBuffer): IntBuffer = {
    for(i <- indexes) i.write(b)
    b
  }

}

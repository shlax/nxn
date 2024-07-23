package org.nxn.model

import org.nxn.vulkan.memory.{ToIntBuffer, TypeLength}

import java.nio.IntBuffer

class VulkanTriangle(val a:Int, val b:Int, val c:Int) extends ToIntBuffer{

  override def toIntBuffer(buff: IntBuffer): IntBuffer = {
    buff.put(a).put(b).put(c)
  }

  def size():Int = {
    TypeLength.intLength.size(3)
  }

}

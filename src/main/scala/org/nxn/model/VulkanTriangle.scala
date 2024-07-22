package org.nxn.model

import org.nxn.vulkan.memory.ToIntBuffer

import java.nio.IntBuffer

class VulkanTriangle(val a:Int, val b:Int, val c:Int) extends ToIntBuffer{

  override def write(b: IntBuffer): IntBuffer = {
    b.put(a).put(b).put(c)
  }

}

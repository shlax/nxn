package org.nxn.model

import org.nxn.math.{Vector2f, Vector3f}
import org.nxn.vulkan.memory.ToFloatBuffer

import java.nio.FloatBuffer

class VulkanVertex(val point:Vector3f, val normal:Vector3f, val uws:Array[Vector2f]) extends ToFloatBuffer {

  override def write(b: FloatBuffer): FloatBuffer = {
    point.write(b)
    normal.write(b)
    for(uw <- uws) uw.write(b)
    b
  }

}

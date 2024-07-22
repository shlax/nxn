package org.nxn.vulkan.memory

import java.nio.FloatBuffer

trait ToFloatBuffer {

  def write(b:FloatBuffer):FloatBuffer
  
}

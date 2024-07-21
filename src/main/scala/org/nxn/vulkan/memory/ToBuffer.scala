package org.nxn.vulkan.memory

import java.nio.FloatBuffer

trait ToBuffer {

  def write(b:FloatBuffer):FloatBuffer
  
}

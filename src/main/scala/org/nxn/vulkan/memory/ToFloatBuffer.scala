package org.nxn.vulkan.memory

import java.nio.FloatBuffer

trait ToFloatBuffer {

  def toFloatBuffer(b:FloatBuffer):FloatBuffer

}

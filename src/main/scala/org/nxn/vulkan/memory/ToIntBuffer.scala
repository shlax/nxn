package org.nxn.vulkan.memory

import java.nio.IntBuffer

trait ToIntBuffer {

  def toIntBuffer(b:IntBuffer):IntBuffer

}

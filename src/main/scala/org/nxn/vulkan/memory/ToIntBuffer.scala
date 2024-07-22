package org.nxn.vulkan.memory

import java.nio.IntBuffer

trait ToIntBuffer {

  def write(b:IntBuffer):IntBuffer

}

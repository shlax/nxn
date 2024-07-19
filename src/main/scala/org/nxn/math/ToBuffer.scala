package org.nxn.math

import java.nio.FloatBuffer

trait ToBuffer {

  def write(b:FloatBuffer):Unit
  
}

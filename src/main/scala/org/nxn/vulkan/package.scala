package org.nxn

import org.lwjgl.PointerBuffer
import org.lwjgl.system.CustomBuffer
import org.lwjgl.vulkan.VK10

package object vulkan {

  def vkCheck(errCode:Int):Unit = {
    if (errCode != VK10.VK_SUCCESS) {
      throw new IllegalStateException(String.format("Vulkan error [0x%X]", errCode));
    }
  }

  extension (a:PointerBuffer){
    inline def putBuffer(b:PointerBuffer):PointerBuffer = {
      a.asInstanceOf[CustomBuffer[PointerBuffer]].put(b)
    }
  }

}

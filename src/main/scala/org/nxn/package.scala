package org

import org.lwjgl.PointerBuffer
import org.lwjgl.system.CustomBuffer
import org.lwjgl.vulkan.VK10

import scala.annotation.targetName

package object nxn {

  extension [T <: AutoCloseable] (ac:T){
    @targetName("withClose")
    inline def | [R](f : T => R ):R = {
      try{
        f.apply(ac)
      }finally {
        ac.close()
      }
    }
  }

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

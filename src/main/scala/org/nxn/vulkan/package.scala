package org.nxn

import org.lwjgl.PointerBuffer
import org.lwjgl.system.CustomBuffer
import org.lwjgl.vulkan.VK10

package object vulkan {

  def vkCheck(errCode:Int):Unit = {
    if (errCode != VK10.VK_SUCCESS) {
      errCode match {
        case VK10.VK_ERROR_OUT_OF_HOST_MEMORY =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_OUT_OF_HOST_MEMORY]"))
        case VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_OUT_OF_DEVICE_MEMORY]"))
        case VK10.VK_ERROR_INITIALIZATION_FAILED =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_INITIALIZATION_FAILED]"))
        case VK10.VK_ERROR_DEVICE_LOST =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_DEVICE_LOST]"))
        case VK10.VK_ERROR_MEMORY_MAP_FAILED =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_MEMORY_MAP_FAILED]"))
        case VK10.VK_ERROR_LAYER_NOT_PRESENT =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_LAYER_NOT_PRESENT]"))
        case VK10.VK_ERROR_EXTENSION_NOT_PRESENT =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_EXTENSION_NOT_PRESENT]"))
        case VK10.VK_ERROR_FEATURE_NOT_PRESENT =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_FEATURE_NOT_PRESENT]"))
        case VK10.VK_ERROR_INCOMPATIBLE_DRIVER =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_INCOMPATIBLE_DRIVER]"))
        case VK10.VK_ERROR_TOO_MANY_OBJECTS =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_TOO_MANY_OBJECTS]"))
        case VK10.VK_ERROR_FORMAT_NOT_SUPPORTED =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_FORMAT_NOT_SUPPORTED]"))
        case VK10.VK_ERROR_FRAGMENTED_POOL =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_FRAGMENTED_POOL]"))
        case VK10.VK_ERROR_UNKNOWN =>
          throw new IllegalStateException(String.format("Vulkan error [VK_ERROR_UNKNOWN]"))
        case _ =>
          throw new IllegalStateException(String.format("Vulkan error [0x%X]", errCode));
      }
    }
  }

  extension (a:PointerBuffer){
    inline def putBuffer(b:PointerBuffer):PointerBuffer = {
      a.asInstanceOf[CustomBuffer[PointerBuffer]].put(b)
    }
  }

}

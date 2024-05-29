package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkDevice}
import org.nxn.*

class NxnDevice(gpu:NxnPhysicalDevice) extends AutoCloseable{

  val device:VkDevice = MemoryStack.stackPush() | { stack =>

    null
  }

  override def close(): Unit = {
    VK10.vkDestroyDevice(device, null)
  }

}

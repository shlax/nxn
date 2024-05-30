package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkQueue}
import org.nxn.*

class NxnQueue(device:NxnDevice, ind:Int) {
  if(ind >= device.queuesFamilies.size){
    throw new IndexOutOfBoundsException(ind)
  }

  val vkQueue:VkQueue = MemoryStack.stackPush() | { stack =>
    val p = stack.callocPointer(1)
    VK10.vkGetDeviceQueue(device.vkDevice, device.queuesFamilies(ind), ind, p)
    new VkQueue(p.get(0), device.vkDevice)
  }

}

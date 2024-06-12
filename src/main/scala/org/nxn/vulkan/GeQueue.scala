package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkQueue}
import org.nxn.Extensions.*

class GeQueue(val device:GeDevice, val index:Int) extends GeContext {
  override val system: GeSystem = device.system

  if(index >= device.queuesFamilies.size){
    throw new IndexOutOfBoundsException(index)
  }

  protected def init(): VkQueue = MemoryStack.stackPush() | { stack =>
    val p = stack.callocPointer(1)
    VK10.vkGetDeviceQueue(device.vkDevice, device.queuesFamilies(index), index, p)
    new VkQueue(p.get(0), device.vkDevice)
  }

  val vkQueue:VkQueue = init()

}

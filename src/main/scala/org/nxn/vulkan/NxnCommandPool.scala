package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandPoolCreateInfo}
import org.nxn.Extensions.*

class NxnCommandPool(val device: NxnDevice) extends NxnContext, AutoCloseable{
  override val engine: NxnEngine = device.engine

  protected def init(): Long = MemoryStack.stackPush() | { stack =>
    val info = VkCommandPoolCreateInfo.calloc(stack)
      .sType$Default()
      .flags(0) //.flags(VK10.VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT)
      .queueFamilyIndex(device.physicalDevice.preferredGraphicsQueueNodeIndex)

    val buff = stack.callocLong(1)
    vkCheck(VK10.vkCreateCommandPool(device.vkDevice, info, null, buff))
    buff.get(0)
  }

  val vkCommandPool: Long = init()

  override def close(): Unit = {
    VK10.vkDestroyCommandPool(device.vkDevice, vkCommandPool, null)
  }
}

package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkBufferCreateInfo, VkMemoryRequirements}

import org.nxn.utils.Using.*

class Buffer(device: Device, size:Int, usage:Int, reqMask:Int) extends Memory(device, size){

  protected def initBufferMemory(size:Int, usage:Int, reqMask:Int): Long = MemoryStack.stackPush() | { stack =>
    val dev = device.physicalDevice

    val info = VkBufferCreateInfo.calloc(stack)
      .sType$Default()
      .sharingMode(if (dev.graphicsQueueIndex == dev.presentQueueIndex) VK10.VK_SHARING_MODE_EXCLUSIVE else VK10.VK_SHARING_MODE_CONCURRENT)
      .usage(usage)
      .size(size)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateBuffer(device.vkDevice, info, null, lp))
    lp.get(0)
  }

  val vkBuffer:Long = initBufferMemory(size, usage, reqMask)

  override val vkMemory: Long = initMemory(reqMask)(new MemoryCallback{
    override def requirements(memReq: VkMemoryRequirements): Unit = {
      VK10.vkGetBufferMemoryRequirements(device.vkDevice, vkBuffer, memReq)
    }
    override def bind(mem: Long): Unit = {
      vkCheck(VK10.vkBindBufferMemory(device.vkDevice, vkBuffer, mem, 0))
    }
  })

  override def close(): Unit = {
    VK10.vkDestroyBuffer(device.vkDevice, vkBuffer, null)
    super.close()
  }
}

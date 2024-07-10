package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkBufferCreateInfo, VkCommandBuffer, VkMemoryAllocateInfo, VkMemoryRequirements, VkPhysicalDeviceMemoryProperties}

import java.util.function.Consumer
import org.nxn.utils.Using.*

class VnBuffer(val device: VnDevice, val size:Int, val usage:Int, val reqMask:Int) extends AutoCloseable{

  protected def initBufferMemory(): (Long, Long) = MemoryStack.stackPush() |{ stack =>
    val dev = device.physicalDevice

    val info = VkBufferCreateInfo.calloc(stack)
      .sType$Default()
      .sharingMode(if(dev.graphicsQueueIndex == dev.presentQueueIndex) VK10.VK_SHARING_MODE_EXCLUSIVE else VK10.VK_SHARING_MODE_CONCURRENT)
      .usage(usage)
      .size(size)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateBuffer(device.vkDevice, info, null, lp))
    val buff = lp.get(0)

    val memReq = VkMemoryRequirements.calloc(stack)
    VK10.vkGetBufferMemoryRequirements(device.vkDevice, buff, memReq)

    val typeBits = memReq.memoryTypeBits()

    var memoryTypeIndex = -1
    val memTypes = dev.memoryTypes
    for(i <- memTypes.indices if memoryTypeIndex == -1){
      if((typeBits & (1 << i)) != 0 && (memTypes(i).propertyFlags & reqMask) == reqMask) {
        memoryTypeIndex = i
      }
    }

    if(memoryTypeIndex == -1){
      throw new RuntimeException("memoryTypeIndex not found")
    }

    val allocInfo = VkMemoryAllocateInfo.calloc(stack)
      .sType$Default()
      .allocationSize(memReq.size())
      .memoryTypeIndex(memoryTypeIndex)

    val alp = stack.callocLong(1)
    vkCheck(VK10.vkAllocateMemory(device.vkDevice, allocInfo, null, alp))
    val mem = alp.get(0)

    vkCheck(VK10.vkBindBufferMemory(device.vkDevice, buff, mem, 0))

    (buff, mem)
  }

  val (buffer:Long,  memory:Long) = initBufferMemory()

  case class MemoryBuffer(address:Long, capacity:Int)

  def map(c:Consumer[MemoryBuffer]):this.type = MemoryStack.stackPush() |{ stack =>
    val vkDevice = device.vkDevice

    val pb = stack.callocPointer(1)
    vkCheck(VK10.vkMapMemory(vkDevice, memory, 0, size, 0, pb))
    val mappedMemory = pb.get(0)

    try {
      c.accept(MemoryBuffer(mappedMemory, size))
    }finally {
      VK10.vkUnmapMemory(vkDevice, memory)
    }

    this
  }

  def bindBuffer(buff: VkCommandBuffer) : Unit = MemoryStack.stackPush() | { stack =>
    VK10.vkCmdBindVertexBuffers(buff, 0, stack.longs(buffer), stack.longs(0L))
  }

  override def close(): Unit = {
    VK10.vkFreeMemory(device.vkDevice, memory, null)
    VK10.vkDestroyBuffer(device.vkDevice, buffer, null)
  }
}

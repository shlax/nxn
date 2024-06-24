package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkBufferCreateInfo, VkMemoryAllocateInfo, VkMemoryRequirements, VkPhysicalDeviceMemoryProperties}
import java.util.function.BiConsumer

import org.nxn.Extensions.*

class GpBuffer(val device: GpDevice, val size:Long, val usage:Int, val reqMask:Int) extends AutoCloseable{

  protected def init(): (Long, Long) = MemoryStack.stackPush() |{ stack =>
    val info = VkBufferCreateInfo.calloc(stack)
      .sType$Default()
      .size(size)
      .usage(usage)
      .sharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateBuffer(device.vkDevice, info, null, lp))
    val buff = lp.get(0)

    val memReq = VkMemoryRequirements.calloc(stack)
    VK10.vkGetBufferMemoryRequirements(device.vkDevice, buff, memReq)

    val memProps = VkPhysicalDeviceMemoryProperties.calloc(stack)
    VK10.vkGetPhysicalDeviceMemoryProperties(device.physicalDevice.vkPhysicalDevice, memProps)

    var typeBits = memReq.memoryTypeBits()
    var memoryTypeIndex = -1

    val memoryTypes = memProps.memoryTypes()
    for(i <- 0 until VK10.VK_MAX_MEMORY_TYPES if memoryTypeIndex == -1){
      if ((typeBits & 1) == 1 && (memoryTypes.get(i).propertyFlags() & reqMask) == reqMask) {
        memoryTypeIndex = i
      }else {
        typeBits >>= 1
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

  val (buffer:Long,  memory:Long) = init()

  def map(c:BiConsumer[Long, MemoryStack]):Unit = MemoryStack.stackPush() |{ stack =>
    val vkDevice = device.vkDevice

    val pb = stack.callocPointer(1)
    vkCheck(VK10.vkMapMemory(vkDevice, memory, 0, size, 0, pb))
    val mappedMemory = pb.get(0)

    try {
      c.accept(mappedMemory, stack)
    }finally {
      VK10.vkUnmapMemory(vkDevice, mappedMemory)
    }
  }

  override def close(): Unit = {
    VK10.vkFreeMemory(device.vkDevice, memory, null)
    VK10.vkDestroyBuffer(device.vkDevice, buffer, null)
  }
}

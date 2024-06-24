package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkCommandBufferAllocateInfo}
import org.nxn.Extensions.*

import java.util.function.Consumer

class GpCommandBuffer(val commandPool: GpCommandPool, val primary:Boolean = true)(fn: Consumer[VkCommandBuffer]) extends AutoCloseable{

  protected def init():VkCommandBuffer = MemoryStack.stackPush() | { stack =>
    val info = VkCommandBufferAllocateInfo.calloc(stack)
      .sType$Default()
      .commandPool(commandPool.vkCommandPool)
      .level(if(primary) VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY else VK10.VK_COMMAND_BUFFER_LEVEL_SECONDARY)
      .commandBufferCount(1)

    val vkDevice = commandPool.device.vkDevice

    val buff = stack.callocPointer(1)
    vkCheck(VK10.vkAllocateCommandBuffers(vkDevice, info, buff))
    val cmdBuff = new VkCommandBuffer(buff.get(0), vkDevice)

    fn.accept(cmdBuff)

    cmdBuff
  }

  val vkCommandBuffer:VkCommandBuffer = init()

  override def close(): Unit = {
    VK10.vkFreeCommandBuffers(commandPool.device.vkDevice, commandPool.vkCommandPool, vkCommandBuffer)
  }

}

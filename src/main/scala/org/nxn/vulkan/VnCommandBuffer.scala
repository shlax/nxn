package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkCommandBufferAllocateInfo}
import org.nxn.utils.Using.*

import java.util.function.Consumer

class VnCommandBuffer(val commandPool: VnCommandPool, val primary:Boolean = true)(fn: Consumer[VkCommandBuffer]) extends AutoCloseable{

  def recording(f: Consumer[VkCommandBuffer]):VnRecording = {
    new VnRecording(f)
  }

  protected def initCommandBuffer(f: Consumer[VkCommandBuffer]):VkCommandBuffer = MemoryStack.stackPush() | { stack =>
    val info = VkCommandBufferAllocateInfo.calloc(stack)
      .sType$Default()
      .commandPool(commandPool.vkCommandPool)
      .level(if(primary) VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY else VK10.VK_COMMAND_BUFFER_LEVEL_SECONDARY)
      .commandBufferCount(1)

    val vkDevice = commandPool.device.vkDevice

    val buff = stack.callocPointer(1)
    vkCheck(VK10.vkAllocateCommandBuffers(vkDevice, info, buff))
    val cmdBuff = new VkCommandBuffer(buff.get(0), vkDevice)

    val rec = recording(f)
    rec.accept(cmdBuff)

    cmdBuff
  }

  val vkCommandBuffer:VkCommandBuffer = initCommandBuffer(fn)

  override def close(): Unit = {
    VK10.vkFreeCommandBuffers(commandPool.device.vkDevice, commandPool.vkCommandPool, vkCommandBuffer)
  }

}

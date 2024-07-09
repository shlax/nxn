package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkCommandBufferAllocateInfo}
import org.nxn.utils.Using.*

import java.util.function.Consumer

class VnCommandBuffer(val commandPool: VnCommandPool, primary:Boolean = true, oneTimeSubmit:Boolean = false)(fn: Consumer[VkCommandBuffer]) extends AutoCloseable{

  def recording(f: Consumer[VkCommandBuffer], oneSubmit:Boolean):VnRecording = {
    new VnRecording(f, oneSubmit)
  }

  protected def initCommandBuffer(f: Consumer[VkCommandBuffer], levelPrimary:Boolean, one:Boolean):VkCommandBuffer = MemoryStack.stackPush() | { stack =>
    val info = VkCommandBufferAllocateInfo.calloc(stack)
      .sType$Default()
      .commandPool(commandPool.vkCommandPool)
      .level(if(levelPrimary) VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY else VK10.VK_COMMAND_BUFFER_LEVEL_SECONDARY)
      .commandBufferCount(1)

    val vkDevice = commandPool.device.vkDevice

    val buff = stack.callocPointer(1)
    vkCheck(VK10.vkAllocateCommandBuffers(vkDevice, info, buff))
    val cmdBuff = new VkCommandBuffer(buff.get(0), vkDevice)

    val rec = recording(f, one)
    rec.accept(cmdBuff)

    cmdBuff
  }

  val vkCommandBuffer:VkCommandBuffer = initCommandBuffer(fn, primary, oneTimeSubmit)

  override def close(): Unit = {
    VK10.vkFreeCommandBuffers(commandPool.device.vkDevice, commandPool.vkCommandPool, vkCommandBuffer)
  }

}

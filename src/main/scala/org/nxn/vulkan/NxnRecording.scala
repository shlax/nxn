package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkCommandBufferBeginInfo}

import java.util.function.Consumer
import org.nxn.Extensions.*

class NxnRecording(fn: Consumer[VkCommandBuffer]) extends Consumer[VkCommandBuffer]{

  protected def info(vkCommandBuffer: VkCommandBuffer, stack:MemoryStack):VkCommandBufferBeginInfo = {
    VkCommandBufferBeginInfo.calloc(stack)
      .sType$Default()
  }

  override def accept(vkCommandBuffer: VkCommandBuffer): Unit = {
    MemoryStack.stackPush() | { stack =>
      val i = info(vkCommandBuffer, stack)
      vkCheck(VK10.vkBeginCommandBuffer(vkCommandBuffer, i))
    }
    try{
      fn.accept(vkCommandBuffer)
    }finally {
      vkCheck(VK10.vkEndCommandBuffer(vkCommandBuffer))
    }
  }

}

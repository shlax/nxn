package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkCommandBufferBeginInfo}

import java.util.function.Consumer
import org.nxn.utils.Using.*

class VnRecording(fn: Consumer[VkCommandBuffer], oneTimeSubmit:Boolean = false) extends Consumer[VkCommandBuffer]{

  protected def info(vkCommandBuffer: VkCommandBuffer, oneSubmit:Boolean, stack:MemoryStack):VkCommandBufferBeginInfo = {
    val info = VkCommandBufferBeginInfo.calloc(stack)
      .sType$Default()

    if(oneSubmit){
      info.flags(VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT)
    }

    info
  }

  override def accept(vkCommandBuffer: VkCommandBuffer): Unit = {
    MemoryStack.stackPush() | { stack =>
      val i = info(vkCommandBuffer, oneTimeSubmit, stack)
      vkCheck(VK10.vkBeginCommandBuffer(vkCommandBuffer, i))
    }
    try{
      fn.accept(vkCommandBuffer)
    }finally {
      vkCheck(VK10.vkEndCommandBuffer(vkCommandBuffer))
    }
  }

}

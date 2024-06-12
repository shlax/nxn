package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkFramebufferCreateInfo}
import org.nxn.Extensions.*

class GeFrameBuffer(val renderPass: GeRenderPass, val imageView:GeImageView) extends GeContext, AutoCloseable {
  override val system: GeSystem = renderPass.system

  protected def init():Long = MemoryStack.stackPush() | { stack =>
    val swapChain = renderPass.swapChain

    val attachments = stack.callocLong(1)
    attachments.put(imageView.vkImageView)
    attachments.flip()
    
    val dim = swapChain.dimension
    
    val info = VkFramebufferCreateInfo.calloc(stack)
      .sType$Default()
      .renderPass(renderPass.vkRenderPass)
      .pAttachments(attachments)
      .width(dim.width)
      .height(dim.height)
      .layers(1)

    val buff = stack.callocLong(1)
    vkCheck(VK10.vkCreateFramebuffer(swapChain.device.vkDevice, info, null, buff))
    buff.get(0)
  }

  val vkFrameBuffer:Long = init()

  override def close(): Unit = {
    VK10.vkDestroyFramebuffer(renderPass.swapChain.device.vkDevice, vkFrameBuffer, null)
  }
}

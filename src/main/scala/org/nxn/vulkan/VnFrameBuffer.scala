package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkFramebufferCreateInfo}
import org.nxn.utils.Using.*

class VnFrameBuffer(val renderPass: VnRenderPass, val imageView:VnImageView) extends AutoCloseable {

  protected def initFrameBuffer():Long = MemoryStack.stackPush() | { stack =>
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

  val vkFrameBuffer:Long = initFrameBuffer()

  override def close(): Unit = {
    VK10.vkDestroyFramebuffer(renderPass.swapChain.device.vkDevice, vkFrameBuffer, null)
  }
}

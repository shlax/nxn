package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkImageSubresourceRange, VkImageViewCreateInfo}
import org.nxn.Extensions.*

import java.util.function.Consumer

class NxnImageView(val swapChain: NxnSwapChain, val ind:Int) extends AutoCloseable, NxnContext {
  override val engine: NxnEngine = swapChain.engine

  if(ind >= swapChain.vkImages.size){
    throw new IndexOutOfBoundsException(ind)
  }

  protected def init() : Long = MemoryStack.stackPush() | { stack =>
    val info = VkImageViewCreateInfo.calloc(stack)
      .sType$Default()
      .image(swapChain.vkImages(ind))
      .viewType(VK10.VK_IMAGE_VIEW_TYPE_2D)
      .format(swapChain.format)
      .subresourceRange( (r: VkImageSubresourceRange) => {
        r.aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT)
          .baseMipLevel(0)
          .levelCount(1)
          .baseArrayLayer(0)
          .layerCount(1)
      }:Unit )

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateImageView(swapChain.device.vkDevice, info, null, lp))
    lp.get(0)
  }

  val vkImageView: Long = init()

  override def close(): Unit = {
    VK10.vkDestroyImageView(swapChain.device.vkDevice, vkImageView, null)
  }

}

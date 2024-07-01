package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}

import java.util.function.Consumer
import org.nxn.utils.Using.*

class VnRenderCommand(val renderPass: VnRenderPass)(fn: Consumer[VkCommandBuffer]) extends AutoCloseable{

  protected def initCommandPool(): VnCommandPool = {
    val dev = renderPass.swapChain.device
    new VnCommandPool(dev, dev.physicalDevice.graphicsQueueIndex)
  }

  val commandPool:VnCommandPool = initCommandPool()

  protected def initCommandBuffers(f: Consumer[VkCommandBuffer]): IndexedSeq[VnCommandBuffer] = MemoryStack.stackPush() | { stack =>

    val clearValues = VkClearValue.calloc(1, stack)
      .apply(0, (v: VkClearValue) => { v.color().float32(0, 0f).float32(1, 0f).float32(2, 0f).float32(3, 1f) })

    val dim = renderPass.swapChain.dimension

    val areaFn = new Consumer[VkRect2D]{
      override def accept(a: VkRect2D): Unit = {
        a.offset().x(0).y(0)
        a.extent().width(dim.width).height(dim.height)
      }
    }

    for (i <- renderPass.frameBuffers.indices) yield {
      val info = VkRenderPassBeginInfo.calloc(stack)
        .sType$Default()
        .renderPass(renderPass.vkRenderPass)
        .pClearValues(clearValues)
        .clearValueCount(1)
        .renderArea(areaFn)
        .framebuffer(renderPass.frameBuffers(i).vkFrameBuffer)

      new VnCommandBuffer(commandPool)({ (vkCommandBuffer:VkCommandBuffer) =>
        VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
        f.accept(vkCommandBuffer)
        VK10.vkCmdEndRenderPass(vkCommandBuffer)
      })
    }
  }

  val commandBuffers: IndexedSeq[VnCommandBuffer] = initCommandBuffers(fn)

  override def close(): Unit = {
    for(c <- commandBuffers) c.close()
    commandPool.close()
  }

}

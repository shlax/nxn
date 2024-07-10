package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}
import org.nxn.vulkan.frame.NextFrame
import java.util.function.Consumer
import org.nxn.utils.Using.*

class VnRenderCommand(val renderPass: VnRenderPass, count:Int = 1) extends AutoCloseable{

  protected def initCommandPool(): VnCommandPool = {
    val dev = renderPass.swapChain.device
    new VnCommandPool(dev, dev.physicalDevice.graphicsQueueIndex, true)
  }

  val commandPool:VnCommandPool = initCommandPool()

  protected def initCommandBuffers(cnt:Int): IndexedSeq[VnCommandBuffer] = MemoryStack.stackPush() | { stack =>
    for (i <- 0 until count) yield  new VnCommandBuffer(commandPool)
  }

  val commandBuffers: IndexedSeq[VnCommandBuffer] = initCommandBuffers(count)

  def record(frame:NextFrame, index:Int = 0)(fn: Consumer[VkCommandBuffer]): VnCommandBuffer  = MemoryStack.stackPush() | { stack =>
    val dim = renderPass.swapChain.dimension

    val clearValues = VkClearValue.calloc(1, stack)
      .apply(0, (v: VkClearValue) => { v.color().float32(0, 0f).float32(1, 0f).float32(2, 0f).float32(3, 1f) })

    val areaFn = new Consumer[VkRect2D] {
      override def accept(a: VkRect2D): Unit = {
        a.offset().x(0).y(0)
        a.extent().width(dim.width).height(dim.height)
      }
    }

    val info = VkRenderPassBeginInfo.calloc(stack)
      .sType$Default()
      .renderPass(renderPass.vkRenderPass)
      .pClearValues(clearValues)
      .clearValueCount(1)
      .renderArea(areaFn)
      .framebuffer(renderPass.frameBuffers(frame.index).vkFrameBuffer)

    val cmdBuff = commandBuffers(index)

    cmdBuff.record(stack)({ (vkCommandBuffer:VkCommandBuffer) =>
      VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
      fn.accept(vkCommandBuffer)
      VK10.vkCmdEndRenderPass(vkCommandBuffer)
    })

    cmdBuff
  }

  override def close(): Unit = {
    for(c <- commandBuffers) c.close()
    commandPool.close()
  }

}

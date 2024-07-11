package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}
import org.nxn.vulkan.frame.NextFrame

import java.util.function.Consumer
import org.nxn.utils.Using.*

class RenderCommand(val renderPass: RenderPass, count:Int = 1) extends AutoCloseable{

  protected def initCommandPool(): CommandPool = {
    val dev = renderPass.swapChain.device
    new CommandPool(dev, dev.physicalDevice.graphicsQueueIndex, true)
  }

  val commandPool:CommandPool = initCommandPool()

  protected def initCommandBuffers(cnt:Int): IndexedSeq[CommandBuffer] = {
    for (i <- 0 until count) yield  new CommandBuffer(commandPool)
  }

  val commandBuffers: IndexedSeq[CommandBuffer] = initCommandBuffers(count)

  def record(frame:NextFrame, index:Int = 0)(fn: Consumer[VkCommandBuffer]): CommandBuffer  = MemoryStack.stackPush() | { stack =>
    val dim = renderPass.swapChain.dimension

    val clearValues = VkClearValue.calloc(1, stack).apply(0, (v: VkClearValue) => {
        v.color().float32(0, 0f).float32(1, 0f).float32(2, 0f).float32(3, 1f)
      })

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

  }

  override def close(): Unit = {
    for(c <- commandBuffers) c.close()
    commandPool.close()
  }

}

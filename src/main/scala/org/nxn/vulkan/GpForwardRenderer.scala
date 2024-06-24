package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}

import java.util.function.Consumer
import org.nxn.Extensions.*

class GpForwardRenderer(val swapChain:GpSwapChain, val commandPool:GpCommandPool) extends AutoCloseable {

  protected def initRenderPass(): GpRenderPass = new GpRenderPass(swapChain)
  val renderPass: GpRenderPass = initRenderPass()

  protected def initFrameBuffers(): IndexedSeq[GpFrameBuffer] = for(i <- swapChain.vkImages.indices) yield new GpFrameBuffer(renderPass, swapChain.imageViews(i))
  val frameBuffers: IndexedSeq[GpFrameBuffer] = initFrameBuffers()

  protected def initFence():GpFence = new GpFence(swapChain.device)
  val fence:GpFence = initFence()

  protected def initCommandBuffers(): IndexedSeq[GpCommandBuffer] = MemoryStack.stackPush() | { stack =>
    val clearValues = VkClearValue.calloc(1, stack)
      .apply(0, (v: VkClearValue) => { v.color().float32(0, 0f).float32(1, 0f).float32(2, 0f).float32(3, 0f) })

    val dim = swapChain.dimension

    val areaFn = new Consumer[VkRect2D]{
      override def accept(a: VkRect2D): Unit = {
        a.extent().set(dim.width, dim.height)
      }
    }

    for (i <- swapChain.vkImages.indices) yield {
      val info = VkRenderPassBeginInfo.calloc(stack)
        .sType$Default()
        .renderPass(renderPass.vkRenderPass)
        .pClearValues(clearValues)
        .renderArea(areaFn)
        .framebuffer(frameBuffers(i).vkFrameBuffer)

      new GpCommandBuffer(commandPool)(new GpRecording({ (vkCommandBuffer:VkCommandBuffer) =>
        VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
        VK10.vkCmdEndRenderPass(vkCommandBuffer)
      }))
    }
  }

  val commandBuffers: IndexedSeq[GpCommandBuffer] = initCommandBuffers()

  def submit(queue: GpQueue, ind:Int):Unit = MemoryStack.stackPush() | { stack =>
    fence.reset()
//    queue.submit(commandBuffers(ind), swapChain.imageAcquisition, swapChain.renderComplete, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT, fence)
  }

  override def close(): Unit = {
    for(i <- frameBuffers) i.close()
    renderPass.close()
    fence.close()
  }

}

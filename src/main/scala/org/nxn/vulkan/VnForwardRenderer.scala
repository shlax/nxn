package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}

import java.util.function.Consumer
import org.nxn.Extensions.*

class VnForwardRenderer(val swapChain:VnSwapChain, val commandPool:VnCommandPool) extends AutoCloseable {

  protected def initRenderPass(): VnRenderPass = new VnRenderPass(swapChain)
  val renderPass: VnRenderPass = initRenderPass()

  protected def initFrameBuffers(): IndexedSeq[VnFrameBuffer] = for(i <- swapChain.vkImages.indices) yield new VnFrameBuffer(renderPass, swapChain.imageViews(i))
  val frameBuffers: IndexedSeq[VnFrameBuffer] = initFrameBuffers()

  protected def initFence():VnFence = new VnFence(swapChain.device)
  val fence:VnFence = initFence()

  protected def initCommandBuffers(): IndexedSeq[VnCommandBuffer] = MemoryStack.stackPush() | { stack =>
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

      new VnCommandBuffer(commandPool)(new VnRecording({ (vkCommandBuffer:VkCommandBuffer) =>
        VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
        VK10.vkCmdEndRenderPass(vkCommandBuffer)
      }))
    }
  }

  val commandBuffers: IndexedSeq[VnCommandBuffer] = initCommandBuffers()

  def submit(queue: VnQueue, ind:Int):Unit = MemoryStack.stackPush() | { stack =>
    fence.reset()
//    queue.submit(commandBuffers(ind), swapChain.imageAcquisition, swapChain.renderComplete, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT, fence)
  }

  override def close(): Unit = {
    for(i <- frameBuffers) i.close()
    renderPass.close()
    fence.close()
  }

}

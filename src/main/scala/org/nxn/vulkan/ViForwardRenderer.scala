package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}

import java.util.function.Consumer
import org.nxn.Extensions.*

class ViForwardRenderer(val swapChain:ViSwapChain, val commandPool:ViCommandPool) extends AutoCloseable {

  protected def initRenderPass(): ViRenderPass = new ViRenderPass(swapChain)
  val renderPass: ViRenderPass = initRenderPass()

  protected def initFrameBuffers(): IndexedSeq[ViFrameBuffer] = for(i <- swapChain.vkImages.indices) yield new ViFrameBuffer(renderPass, swapChain.imageViews(i))
  val frameBuffers: IndexedSeq[ViFrameBuffer] = initFrameBuffers()

  protected def initFence():ViFence = new ViFence(swapChain.device)
  val fence:ViFence = initFence()

  protected def initCommandBuffers(): IndexedSeq[ViCommandBuffer] = MemoryStack.stackPush() | { stack =>
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

      new ViCommandBuffer(commandPool)(new ViRecording({ (vkCommandBuffer:VkCommandBuffer) =>
        VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
        VK10.vkCmdEndRenderPass(vkCommandBuffer)
      }))
    }
  }

  val commandBuffers: IndexedSeq[ViCommandBuffer] = initCommandBuffers()

  def submit(queue: ViQueue, ind:Int):Unit = MemoryStack.stackPush() | { stack =>
    fence.reset()
//    queue.submit(commandBuffers(ind), swapChain.imageAcquisition, swapChain.renderComplete, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT, fence)
  }

  override def close(): Unit = {
    for(i <- frameBuffers) i.close()
    renderPass.close()
    fence.close()
  }

}

package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkClearValue, VkCommandBuffer, VkRect2D, VkRenderPassBeginInfo}

import java.util.function.Consumer
import org.nxn.Extensions.*

class GeForwardRenderer(val swapChain:GeSwapChain, val commandPool:GeCommandPool) extends GeContext, AutoCloseable {
  override val system: GeSystem = swapChain.system

  protected def initRenderPass(): GeRenderPass = new GeRenderPass(swapChain)
  val renderPass: GeRenderPass = initRenderPass()

  protected def initImageViews(): IndexedSeq[GeImageView] = for(i <- swapChain.vkImages.indices) yield new GeImageView(swapChain, i)
  val imageViews: IndexedSeq[GeImageView] = initImageViews()

  protected def initFrameBuffers(): IndexedSeq[GeFrameBuffer] = for(i <- swapChain.vkImages.indices) yield new GeFrameBuffer(renderPass, imageViews(i))
  val frameBuffers: IndexedSeq[GeFrameBuffer] = initFrameBuffers()

  protected def initFence():GeFence = new GeFence(swapChain.device)
  val fences:GeFence = initFence()

  protected def initCommandBuffers(): IndexedSeq[GeCommandBuffer] = MemoryStack.stackPush() | { stack =>
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

      new GeCommandBuffer(commandPool)(new GeRecording({ (vkCommandBuffer:VkCommandBuffer) =>
        VK10.vkCmdBeginRenderPass(vkCommandBuffer, info, VK10.VK_SUBPASS_CONTENTS_INLINE)
        VK10.vkCmdEndRenderPass(vkCommandBuffer)
      }))
    }
  }

  val commandBuffers: IndexedSeq[GeCommandBuffer] = initCommandBuffers()

  override def close(): Unit = {
    for(i <- frameBuffers) i.close()
    for(i <- imageViews) i.close()
    renderPass.close()
    fences.close()
  }

}

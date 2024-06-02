package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10
import org.nxn.*

class NxnImageView(swapChain: NxnSwapChain, ind:Int) extends AutoCloseable, NxnContext {
  override val engine: NxnEngine = swapChain.engine

  val vkImageView: Long = MemoryStack.stackPush() | { stack =>
    // https://github.com/LWJGL/lwjgl3/blob/master/modules/samples/src/test/java/org/lwjgl/demo/vulkan/HelloVulkan.java#L872
    // https://github.com/lwjglgamedev/vulkanbook/blob/master/booksamples/chapter-04/src/main/java/org/vulkanb/eng/graph/vk/ImageView.java#L58
    // https://github.com/lwjglgamedev/vulkanbook/blob/master/bookcontents/chapter-04/chapter-04.md
    0
  }

  override def close(): Unit = {
    VK10.vkDestroyImageView(swapChain.device.vkDevice, vkImageView, null)
  }

}

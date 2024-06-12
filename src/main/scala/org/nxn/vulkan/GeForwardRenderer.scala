package org.nxn.vulkan

class GeForwardRenderer(val swapChain:GeSwapChain, val commandPool:GeCommandPool) extends GeContext, AutoCloseable {
  override val system: GeSystem = swapChain.system

  val renderPass: GeRenderPass = new GeRenderPass(swapChain)



  override def close(): Unit = {
    renderPass.close()
  }

}

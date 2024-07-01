package org.nxn.vulkan

import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.KHRSurface
import org.nxn.utils.Using.*

class VnSurface(val instance:VnInstance, val window:VnWindow) extends AutoCloseable{

  protected def initSurface():Long = MemoryStack.stackPush()|{ stack =>
    val b = stack.callocLong(1)
    vkCheck(GLFWVulkan.glfwCreateWindowSurface(instance.vkInstance, window.windowHandle, null, b))
    b.get(0)
  }

  val vkSurface:Long = initSurface()

  override def close(): Unit = {
    KHRSurface.vkDestroySurfaceKHR(instance.vkInstance, vkSurface, null)
  }
}

package org.nxn.vulkan

import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.KHRSurface
import org.nxn.Extensions.*

class ViSurface(val instance:ViInstance, val window:ViWindow) extends AutoCloseable{

  protected def init():Long = MemoryStack.stackPush()|{ stack =>
    val b = stack.callocLong(1)
    vkCheck(GLFWVulkan.glfwCreateWindowSurface(instance.vkInstance, window.windowHandle, null, b))
    b.get(0)
  }

  val vkSurface:Long = init()

  override def close(): Unit = {
    KHRSurface.vkDestroySurfaceKHR(instance.vkInstance, vkSurface, null)
  }
}

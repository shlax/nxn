package org.nxn.vulkan

import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.KHRSurface
import org.nxn.Extensions.*

class GeSurface(val instance:GeInstance, val window:GeWindow) extends GeContext, AutoCloseable{
  override val system: GeSystem = instance.system

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

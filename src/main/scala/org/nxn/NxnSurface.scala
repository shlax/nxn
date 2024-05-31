package org.nxn

import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.KHRSurface
import org.nxn.*

class NxnSurface(instance:NxnInstance, win:NxnWindow) extends AutoCloseable, NxnContext{
  override val engine: NxnEngine = instance.engine

  val vkSurface:Long = MemoryStack.stackPush()|{ stack =>
    val b = stack.callocLong(1)
    vkCheck(GLFWVulkan.glfwCreateWindowSurface(instance.vkInstance, win.windowHandle, null, b))
    b.get(0)
  }

  override def close(): Unit = {
    KHRSurface.vkDestroySurfaceKHR(instance.vkInstance, vkSurface, null)
  }
}

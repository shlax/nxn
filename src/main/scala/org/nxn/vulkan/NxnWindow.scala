package org.nxn.vulkan

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil

class NxnWindow(en:NxnEngine) extends AutoCloseable, NxnContext{
  override val engine: NxnEngine = en

  GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)
  GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)

  protected def init():Long = {
    GLFW.glfwCreateWindow(en.size.width, en.size.height, en.name, MemoryUtil.NULL, MemoryUtil.NULL)
  }

  val windowHandle:Long = init()
  if (windowHandle == MemoryUtil.NULL){
    throw new IllegalStateException("Failed to create the GLFW window")
  }

  override def close(): Unit = {
    Callbacks.glfwFreeCallbacks(windowHandle)
    GLFW.glfwDestroyWindow(windowHandle)
  }

}

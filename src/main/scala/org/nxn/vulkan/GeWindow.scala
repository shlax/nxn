package org.nxn.vulkan

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil

class GeWindow(en:GeSystem) extends GeContext, AutoCloseable{
  override val system: GeSystem = en

  GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)
  GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)

  protected def init():Long = {
    val s = en.windowSize
    GLFW.glfwCreateWindow(s.width, s.height, en.name, MemoryUtil.NULL, MemoryUtil.NULL)
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

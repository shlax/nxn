package org.nxn.vulkan

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil

class VnWindow(val system:VnSystem) extends  AutoCloseable{

  GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)
  GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)

  protected def init():Long = {
    val s = system.windowSize
    GLFW.glfwCreateWindow(s.width, s.height, system.name, MemoryUtil.NULL, MemoryUtil.NULL)
  }

  val windowHandle:Long = init()
  if (windowHandle == MemoryUtil.NULL){
    throw new IllegalStateException("Failed to create the GLFW window")
  }

  def pullEvents() :Boolean = {
    val res = !GLFW.glfwWindowShouldClose(windowHandle)
    if(res) GLFW.glfwPollEvents()
    res
  }

  override def close(): Unit = {
    Callbacks.glfwFreeCallbacks(windowHandle)
    GLFW.glfwDestroyWindow(windowHandle)
  }

}

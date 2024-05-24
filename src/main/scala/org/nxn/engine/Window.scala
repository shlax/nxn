package org.nxn.engine

import org.lwjgl.glfw.{Callbacks, GLFW, GLFWVulkan}
import org.lwjgl.system.MemoryUtil

class Window extends AutoCloseable{
  if (!GLFW.glfwInit) throw new IllegalStateException("Unable to initialize GLFW")
  if (!GLFWVulkan.glfwVulkanSupported) throw new IllegalStateException("Cannot find a compatible Vulkan installable client driver (ICD)")

//  private val vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor)
  GLFW.glfwDefaultWindowHints()
  GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API)
  GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE)

  private val windowHandle = GLFW.glfwCreateWindow(1280, 720, "", MemoryUtil.NULL, MemoryUtil.NULL)
  if (windowHandle == MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window")

//  GLFW.glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) => { }  )



  override def close(): Unit = {
    Callbacks.glfwFreeCallbacks(windowHandle)
    GLFW.glfwDestroyWindow(windowHandle)
    GLFW.glfwTerminate()
  }
}

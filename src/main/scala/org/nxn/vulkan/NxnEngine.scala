package org.nxn.vulkan

import org.lwjgl.glfw.{GLFW, GLFWErrorCallback, GLFWVulkan}
import org.nxn.utils.Dimension


class NxnEngine(val debug :Boolean, val name:String, val size:Dimension) extends AutoCloseable{
  GLFWErrorCallback.createPrint.set()

  if (!GLFW.glfwInit()){
    throw new IllegalStateException("Unable to initialize GLFW")
  }

  if (!GLFWVulkan.glfwVulkanSupported()){
    throw new IllegalStateException("Cannot find a compatible Vulkan installable client driver (ICD)")
  }

  override def close(): Unit = {
    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null).free()
  }
}

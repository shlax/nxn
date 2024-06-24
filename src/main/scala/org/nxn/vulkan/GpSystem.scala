package org.nxn.vulkan

import org.lwjgl.glfw.{GLFW, GLFWErrorCallback, GLFWVulkan}
import org.nxn.utils.Dimension

import scala.concurrent.duration.Duration
import scala.concurrent.duration.*

class GpSystem(val debug :Boolean, val name:String, val windowSize:Dimension, val timeout:Duration = 1.second) extends AutoCloseable{
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

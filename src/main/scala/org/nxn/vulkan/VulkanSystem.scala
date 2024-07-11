package org.nxn.vulkan

import org.lwjgl.glfw.{GLFW, GLFWErrorCallback, GLFWVulkan}
import org.nxn.utils.Dimension

import scala.concurrent.duration.Duration
import scala.concurrent.duration.*

class VulkanSystem(val name:String, val windowSize:Dimension, val debug :LogLevel = LogLevel.warning, val timeout:Duration = 1.second) extends AutoCloseable{
  GLFWErrorCallback.createPrint.set()

  if (!GLFW.glfwInit()){
    throw new IllegalStateException("Unable to initialize GLFW")
  }

  if (!GLFWVulkan.glfwVulkanSupported()){
    throw new IllegalStateException("Cannot find a compatible Vulkan installable client driver (ICD)")
  }

  protected def initInstance() : Instance = {
    new Instance(this)
  }

  val instance:Instance = initInstance()

  protected def initWindow(): GlfwWindow = {
    new GlfwWindow(this)
  }

  val window: GlfwWindow = initWindow()

  protected def initSurface(): Surface = {
    new Surface(instance, window)
  }

  val surface:Surface = initSurface()

  protected def initDevice():Device = {
    new Device(instance, surface)
  }

  val device:Device = initDevice()

  protected def initSwapChain():SwapChain = {
    new SwapChain(surface, device)
  }

  val swapChain:SwapChain = initSwapChain()

  protected def initRenderPass():RenderPass = {
    new RenderPass(swapChain)
  }

  val renderPass:RenderPass = initRenderPass()

  override def close(): Unit = {
    renderPass.close()
    swapChain.close()
    device.close()
    surface.close()
    window.close()
    instance.close()

    GLFW.glfwTerminate()
    GLFW.glfwSetErrorCallback(null).free()
  }
}

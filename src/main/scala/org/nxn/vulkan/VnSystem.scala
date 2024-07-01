package org.nxn.vulkan

import org.lwjgl.glfw.{GLFW, GLFWErrorCallback, GLFWVulkan}
import org.nxn.utils.Dimension

import scala.concurrent.duration.Duration
import scala.concurrent.duration.*

class VnSystem(val debug :Boolean, val name:String, val windowSize:Dimension, val timeout:Duration = 1.second) extends AutoCloseable{
  GLFWErrorCallback.createPrint.set()

  if (!GLFW.glfwInit()){
    throw new IllegalStateException("Unable to initialize GLFW")
  }

  if (!GLFWVulkan.glfwVulkanSupported()){
    throw new IllegalStateException("Cannot find a compatible Vulkan installable client driver (ICD)")
  }

  protected def initInstance() : VnInstance = {
    new VnInstance(this)
  }

  val instance:VnInstance = initInstance()

  protected def initWindow(): VnWindow = {
    new VnWindow(this)
  }

  val window: VnWindow = initWindow()

  protected def initSurface(): VnSurface = {
    new VnSurface(instance, window)
  }

  val surface:VnSurface = initSurface()

  protected def initDevice():VnDevice = {
    new VnDevice(instance, surface)
  }

  val device:VnDevice = initDevice()

  protected def initSwapChain():VnSwapChain = {
    new VnSwapChain(surface, device)
  }

  val swapChain:VnSwapChain = initSwapChain()

  protected def initRenderPass():VnRenderPass = {
    new VnRenderPass(swapChain)
  }

  val renderPass:VnRenderPass = initRenderPass()

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

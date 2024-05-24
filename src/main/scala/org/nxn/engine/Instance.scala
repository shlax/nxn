package org.nxn.engine

import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.{CustomBuffer, MemoryStack}
import org.lwjgl.vulkan.{VK10, VkLayerProperties}
import org.nxn.*

class Instance(debug:Boolean) extends AutoCloseable{

  private var layers:List[String] = Nil
  if(debug) {
    MemoryStack.stackPush() | { stack =>
      val numLayersArr = stack.callocInt(1)
      VK10.vkEnumerateInstanceLayerProperties(numLayersArr, null)
      val numLayers = numLayersArr.get(0)

      val propsBuf = VkLayerProperties.calloc(numLayers, stack)
      VK10.vkEnumerateInstanceLayerProperties(numLayersArr, propsBuf)

      for (i <- 0 until numLayers) {
        val p = propsBuf.get(i)
        val nm = p.layerNameString()
        if (nm == "VK_LAYER_KHRONOS_validation") {
          layers = nm :: layers
        }
      }
    }
  }

  MemoryStack.stackPush() | { stack =>
    val glfwExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions()
    if (glfwExtensions == null) throw new RuntimeException("Failed to find the GLFW platform surface extensions");

    val requiredExtensions = stack.mallocPointer(layers.size + 1)
    for (nm <- layers) {
      val requiredLayers = stack.mallocPointer(1)
      requiredLayers.put(0, stack.ASCII(nm))
      requiredExtensions.putBuffer(requiredLayers)
    }
    requiredExtensions.putBuffer(glfwExtensions)
  }

  override def close(): Unit = {

  }

}

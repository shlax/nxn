package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkShaderModuleCreateInfo}
import org.nxn.Extensions.*

class ViShaderModule(val device: ViDevice, data:Array[Byte]) extends AutoCloseable{

  protected def init(dt:Array[Byte]):Long = MemoryStack.stackPush()|{ stack =>
    val pCode = stack.malloc(dt.length).put(0, dt)

    val info = VkShaderModuleCreateInfo.calloc(stack)
      .sType$Default()
      .pCode(pCode)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateShaderModule(device.vkDevice, info, null, lp))
    lp.get()
  }

  val shaderModule:Long = init(data)

  override def close(): Unit = {
    VK10.vkDestroyShaderModule(device.vkDevice, shaderModule, null)
  }
}

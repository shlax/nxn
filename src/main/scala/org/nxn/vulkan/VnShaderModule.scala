package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkShaderModuleCreateInfo}
import org.nxn.Extensions.*

/** allowed to destroy the shader modules again as soon as pipeline creation is finished */
class VnShaderModule(val device: VnDevice, val stage:Int, val name:String, data:Array[Byte]) extends AutoCloseable{

  protected def init(dt:Array[Byte]):Long = MemoryStack.stackPush()|{ stack =>
    val pCode = stack.calloc(dt.length).put(0, dt)

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

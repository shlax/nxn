package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkShaderModuleCreateInfo}
import org.nxn.utils.Using.*
import org.nxn.vulkan.shader.CompiledShader

class VnShaderModule(val device: VnDevice, compiledShader: CompiledShader) extends AutoCloseable{
  val stage: Int = compiledShader.stage
  val name: String = compiledShader.name

  protected def initShaderModule(dt:Array[Byte]):Long = MemoryStack.stackPush()|{ stack =>
    val pCode = stack.calloc(dt.length).put(0, dt)

    val info = VkShaderModuleCreateInfo.calloc(stack)
      .sType$Default()
      .pCode(pCode)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateShaderModule(device.vkDevice, info, null, lp))
    lp.get()
  }

  val shaderModule:Long = initShaderModule(compiledShader.code)

  override def close(): Unit = {
    VK10.vkDestroyShaderModule(device.vkDevice, shaderModule, null)
  }
}

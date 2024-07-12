package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkDescriptorSetAllocateInfo}
import org.nxn.utils.Using.*

class DescriptorSet(val pool:DescriptorPool) extends AutoCloseable{

  protected def initDescriptorLayout():Long = MemoryStack.stackPush() | { stack =>


    val info = VkDescriptorSetAllocateInfo.calloc(stack)
      .sType$Default()
      .descriptorPool(pool.vkDescriptorPool)
      .pSetLayouts(???)

    val lb = stack.callocLong(1)
    vkCheck(VK10.vkAllocateDescriptorSets(pool.device.vkDevice, info, lb))
    lb.get(0)
  }

  val vkDescriptorLayout:Long = initDescriptorLayout()

  override def close(): Unit = {
    VK10.vkDestroyDescriptorSetLayout(pool.device.vkDevice, vkDescriptorLayout, null)
  }
}

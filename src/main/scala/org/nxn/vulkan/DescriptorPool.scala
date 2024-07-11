package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkDescriptorPoolCreateInfo, VkDescriptorPoolSize, VkDescriptorSetAllocateInfo, VkDescriptorSetLayoutBinding, VkDescriptorSetLayoutCreateInfo}
import org.nxn.utils.Using.*

/** poolTypes : Map(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER -> 1) */
class DescriptorPool(val device: Device, poolTypes:Map[Int,Int], maxSets:Int = 1) extends AutoCloseable{

  protected def initDescriptorPool(types:Map[Int,Int], sets:Int):Long = MemoryStack.stackPush() | { stack =>
    val poolSizes = VkDescriptorPoolSize.calloc(types.size, stack)
    for(i <- types.zipWithIndex) {
      val e = i._1
      poolSizes.get(i._2) // index
        .`type`(e._1)
        .descriptorCount(e._2)
    }

    val info = VkDescriptorPoolCreateInfo.calloc(stack)
      .sType$Default()
      .pPoolSizes(poolSizes)
      .maxSets(sets)

    val lb = stack.callocLong(1)
    vkCheck(VK10.vkCreateDescriptorPool(device.vkDevice, info, null, lb))
    lb.get(0)
  }

  val vkDescriptorPool:Long = initDescriptorPool(poolTypes, maxSets)

  override def close(): Unit = {
    VK10.vkDestroyDescriptorPool(device.vkDevice, vkDescriptorPool, null)
  }

}

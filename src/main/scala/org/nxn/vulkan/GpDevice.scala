package org.nxn.vulkan

import org.lwjgl.PointerBuffer
import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.lwjgl.vulkan.{KHRSwapchain, VK10, VkDevice, VkDeviceCreateInfo, VkDeviceQueueCreateInfo, VkPhysicalDeviceFeatures}
import org.nxn.Extensions.*

class GpDevice(val physicalDevice:GpPhysicalDevice) extends GpContext, AutoCloseable{
  override val system: GpSystem = physicalDevice.system

  val queuesFamilies:IndexedSeq[Int] = physicalDevice.queuesFamilies()

  protected def init(): VkDevice = MemoryStack.stackPush() | { stack =>
    val queue = VkDeviceQueueCreateInfo.calloc(queuesFamilies.size, stack)

    val priorities = stack.callocFloat(queuesFamilies.size)
    for(_ <- queuesFamilies.indices) priorities.put(1f)
    priorities.flip()

    for(i <- queuesFamilies.zipWithIndex) {
      queue.get(i._2)
        .sType$Default()
        .queueFamilyIndex(i._1)
        .pQueuePriorities(priorities)
        .pNext(MemoryUtil.NULL)
        .flags(0)
    }

    val deviceFeatures = VkPhysicalDeviceFeatures.calloc(stack)
    VK10.vkGetPhysicalDeviceFeatures(physicalDevice.vkPhysicalDevice, deviceFeatures)
    val enabledFeatures = features(deviceFeatures, stack)

    val extNm = stack.callocPointer(1)
    extNm.put(MemoryUtil.memASCII(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME))
    extNm.flip()

    var enabledLayerNames:Option[PointerBuffer] = None
    if(system.debug){
      val p = stack.callocPointer(1)
      p.put(stack.ASCII("VK_LAYER_KHRONOS_validation"))
      p.flip()
      enabledLayerNames = Some(p)
    }

    val devInf = VkDeviceCreateInfo.calloc(stack)
      .sType$Default()
      .pQueueCreateInfos(queue)
      .ppEnabledLayerNames(enabledLayerNames.orNull)
      .pEnabledFeatures(enabledFeatures)
      .ppEnabledExtensionNames(extNm)
      .pNext(MemoryUtil.NULL)
      .flags(0)

    val buff = stack.callocPointer(1)
    vkCheck(VK10.vkCreateDevice(physicalDevice.vkPhysicalDevice, devInf, null, buff))

    new VkDevice(buff.get(0), physicalDevice.vkPhysicalDevice, devInf)
  }

  val vkDevice:VkDevice = init()

  protected def features(deviceFeatures:VkPhysicalDeviceFeatures, stack:MemoryStack) : VkPhysicalDeviceFeatures = {
    deviceFeatures
  }

  protected def graphicsQueueInit():GpQueue = {
    new GpQueue(this, physicalDevice.graphicsQueueIndex, 0)
  }

  val graphicsQueue:GpQueue = graphicsQueueInit()

  protected def presentQueueInit(): GpQueue = {
    if(physicalDevice.graphicsQueueIndex == physicalDevice.presentQueueIndex){
      graphicsQueue
    }else {
      new GpQueue(this, physicalDevice.presentQueueIndex, 0)
    }
  }

  val presentQueue:GpQueue = presentQueueInit()

  override def close(): Unit = {
    VK10.vkDestroyDevice(vkDevice, null)
  }

}

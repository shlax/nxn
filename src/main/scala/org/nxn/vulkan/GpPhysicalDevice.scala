package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{KHRSurface, KHRSwapchain, VK10, VkExtensionProperties, VkPhysicalDevice, VkQueueFamilyProperties}
import org.nxn.Extensions.*

class GpPhysicalDevice(val instance: GpInstance, val surface: GpSurface){

  /** vkPhysicalDevice:VkPhysicalDevice,
   graphicsQueueIndex:Int, graphicsQueueIndexes:IndexedSeq[Int],
   presentQueueIndex:Int, presentQueueIndexes:IndexedSeq[Int] */
  protected def init():(VkPhysicalDevice, Int, IndexedSeq[Int], Int, IndexedSeq[Int]) = MemoryStack.stackPush()|{ stack =>
    val vkInstance = instance.vkInstance

    val nBuff = stack.callocInt(1)
    vkCheck(VK10.vkEnumeratePhysicalDevices(vkInstance, nBuff, null))
    val n = nBuff.get(0)
    if(n <= 0){
      throw new IllegalStateException("vkEnumeratePhysicalDevices reported zero accessible devices.")
    }

    val devices = stack.callocPointer(n)
    vkCheck(VK10.vkEnumeratePhysicalDevices(vkInstance, nBuff, devices))

    var vkGpu:Option[(VkPhysicalDevice, Int, IndexedSeq[Int], Int, IndexedSeq[Int])] = None

    for(i <- 0 until n){
      val gpu = new VkPhysicalDevice(devices.get(i), vkInstance)
      val intBuff = stack.callocInt(1)

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(gpu, intBuff, null)
      val props = VkQueueFamilyProperties.calloc(intBuff.get(0), stack)
      VK10.vkGetPhysicalDeviceQueueFamilyProperties(gpu, intBuff, props)

      var graphicsQueueInd:List[Int] = Nil
      var presentQueueInd:List[Int] = Nil

      for(i <- 0 until props.capacity()){
        val p = props.get(i)
        if( (p.queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0 ){
          graphicsQueueInd = i :: graphicsQueueInd
        }

        val supportsPresent = stack.callocInt(1)
        vkCheck(KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(gpu, i, surface.vkSurface, supportsPresent))
        if (supportsPresent.get(0) == VK10.VK_TRUE) {
          presentQueueInd = i :: presentQueueInd
        }
      }

      var hasKHRSwapChainExtension = false
      if(graphicsQueueInd.nonEmpty && presentQueueInd.nonEmpty){
        vkCheck(VK10.vkEnumerateDeviceExtensionProperties(gpu, null.asInstanceOf[CharSequence], intBuff, null))
        val ext = VkExtensionProperties.calloc(intBuff.get(0), stack)
        vkCheck(VK10.vkEnumerateDeviceExtensionProperties(gpu, null.asInstanceOf[CharSequence], intBuff, ext))

        for(i <- 0 until ext.capacity() if !hasKHRSwapChainExtension){
          val e = ext.get(i)
          if(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME == e.extensionNameString()){
            hasKHRSwapChainExtension = true
          }
        }
      }

      if(hasKHRSwapChainExtension){
        val int = graphicsQueueInd.intersect(presentQueueInd)
        val gInd = if(int.isEmpty) graphicsQueueInd.head else int.head
        val pInd = if(int.isEmpty) presentQueueInd.head else int.head

        if(vkGpu.isEmpty || ( int.nonEmpty && vkGpu.get._2 != vkGpu.get._4 )){
          vkGpu = Some((gpu, gInd, graphicsQueueInd.toIndexedSeq, pInd, presentQueueInd.toIndexedSeq))
        }
      }

    }

    vkGpu.get
  }

  val (vkPhysicalDevice:VkPhysicalDevice,
    graphicsQueueIndex:Int, graphicsQueueIndexes:IndexedSeq[Int],
    presentQueueIndex:Int, presentQueueIndexes:IndexedSeq[Int]) = init()

  def queuesFamilies() :IndexedSeq[Int] = {
    Set(graphicsQueueIndex, presentQueueIndex).toIndexedSeq
  }

}

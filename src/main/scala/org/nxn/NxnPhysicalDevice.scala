package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{KHRSurface, KHRSwapchain, VK10, VkExtensionProperties, VkPhysicalDevice, VkQueueFamilyProperties}
import org.nxn.*

class NxnPhysicalDevice(val instance: NxnInstance, surface: NxnSurface) extends NxnContext{
  override val engine: NxnEngine = instance.engine

  val (vkPhysicalDevice:VkPhysicalDevice,
    preferredGraphicsQueueNodeIndex:Int, graphicsQueueNodeIndexes:List[Int],
    preferredPresentQueueNodeIndex:Int, presentQueueNodeIndexes:List[Int]) = MemoryStack.stackPush()|{ stack =>

    val vkInstance = instance.vkInstance

    val nBuff = stack.callocInt(1)
    vkCheck(VK10.vkEnumeratePhysicalDevices(vkInstance, nBuff, null))
    val n = nBuff.get(0)
    if(n <= 0){
      throw new IllegalStateException("vkEnumeratePhysicalDevices reported zero accessible devices.")
    }

    val devices = stack.callocPointer(n)
    vkCheck(VK10.vkEnumeratePhysicalDevices(vkInstance, nBuff, devices))

    var vkGpu:Option[(VkPhysicalDevice, Int, List[Int], Int, List[Int])] = None

    for(i <- 0 until n){
      val gpu = new VkPhysicalDevice(devices.get(i), vkInstance)
      val intBuff = stack.callocInt(1)

      VK10.vkGetPhysicalDeviceQueueFamilyProperties(gpu, intBuff, null)
      val props = VkQueueFamilyProperties.calloc(intBuff.get(0), stack)
      VK10.vkGetPhysicalDeviceQueueFamilyProperties(gpu, intBuff, props)

      var graphicsQueueNodeInd:List[Int] = Nil
      var presentQueueNodeInd:List[Int] = Nil

      for(i <- 0 until props.capacity()){
        val p = props.get(i)
        if( (p.queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0 ){
          graphicsQueueNodeInd = i :: graphicsQueueNodeInd
        }

        val supportsPresent = stack.callocInt(1)
        vkCheck(KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(gpu, i, surface.vkSurface, supportsPresent))
        if (supportsPresent.get(0) == VK10.VK_TRUE) {
          presentQueueNodeInd = i :: presentQueueNodeInd
        }
      }

      var hasKHRSwapChainExtension = false
      if(graphicsQueueNodeInd.nonEmpty && presentQueueNodeInd.nonEmpty){
        vkCheck(VK10.vkEnumerateDeviceExtensionProperties(gpu, null.asInstanceOf[CharSequence], intBuff, null))
        val ext = VkExtensionProperties.calloc(intBuff.get(0), stack)
        vkCheck(VK10.vkEnumerateDeviceExtensionProperties(gpu, null.asInstanceOf[CharSequence], intBuff, ext))

        for(i <- 0 until ext.capacity() if !hasKHRSwapChainExtension){
          val e = ext.get(0)
          if(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME == e.extensionNameString()){
            hasKHRSwapChainExtension = true
          }
        }
      }

      if(hasKHRSwapChainExtension){
        val int = graphicsQueueNodeInd.intersect(presentQueueNodeInd)
        val gInd = if(int.isEmpty) graphicsQueueNodeInd.head else int.head
        val pInd = if(int.isEmpty) presentQueueNodeInd.head else int.head

        if(vkGpu.isEmpty || ( int.nonEmpty && vkGpu.get._2 != vkGpu.get._4 )){
          vkGpu = Some((gpu, gInd, graphicsQueueNodeInd, pInd, presentQueueNodeInd))
        }
      }

    }

    vkGpu.get
  }

}

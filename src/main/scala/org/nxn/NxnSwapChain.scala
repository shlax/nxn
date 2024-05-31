package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{KHRSurface, KHRSwapchain, VK10, VkExtent2D, VkSurfaceCapabilitiesKHR, VkSurfaceFormatKHR, VkSwapchainCreateInfoKHR}
import org.nxn.*

class NxnSwapChain(surface: NxnSurface, device: NxnDevice, imgCount:Int) extends NxnContext {
  override val engine: NxnEngine = device.engine

  val vkSwapChain : Long = MemoryStack.stackPush() | { stack =>
    val vkPhysicalDevice = device.physicalDevice.vkPhysicalDevice

    val surfCapabilities = VkSurfaceCapabilitiesKHR.calloc(stack)
    vkCheck(KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(vkPhysicalDevice, surface.vkSurface, surfCapabilities))

    val maxImages = surfCapabilities.maxImageCount()
    val minImages = surfCapabilities.minImageCount()
    val imgCnt = (if( maxImages == 0 ) imgCount else imgCount.min(maxImages)).max(minImages)

    val formatsBuff = stack.callocInt(1)
    vkCheck(KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(vkPhysicalDevice, surface.vkSurface, formatsBuff, null))
    val numFormats = formatsBuff.get(0)
    if(numFormats <= 0){
      throw new RuntimeException("vkGetPhysicalDeviceSurfaceFormatsKHR pSurfaceFormatCount is "+numFormats)
    }

    val surfaceFormats = VkSurfaceFormatKHR.calloc(numFormats, stack)
    vkCheck(KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(vkPhysicalDevice, surface.vkSurface, formatsBuff, surfaceFormats))

    var imageFormat = surfaceFormats.get(0).format()
    var colorSpace = surfaceFormats.get(0).colorSpace()
    for(i <- 0 until numFormats){
      val sf = surfaceFormats.get(i)
      if(sf.format() == VK10.VK_FORMAT_B8G8R8A8_UNORM && sf.colorSpace() == KHRSurface.VK_COLOR_SPACE_SRGB_NONLINEAR_KHR){
        imageFormat = sf.format()
        colorSpace = sf.colorSpace()
      }
    }

    val presentModesBuff = stack.callocInt(1)
    vkCheck(KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(vkPhysicalDevice, surface.vkSurface, presentModesBuff, null))
    val mumPresentModes = presentModesBuff.get(0)

    val presentModes = stack.mallocInt(mumPresentModes)
    vkCheck(KHRSurface.vkGetPhysicalDeviceSurfacePresentModesKHR(vkPhysicalDevice, surface.vkSurface, presentModesBuff, presentModes))

    val modes = for(i <- 0 until mumPresentModes) yield presentModes.get(i)
    val presentMode = if(modes.contains(KHRSurface.VK_PRESENT_MODE_FIFO_RELAXED_KHR)) KHRSurface.VK_PRESENT_MODE_FIFO_RELAXED_KHR
      else if(modes.contains(KHRSurface.VK_PRESENT_MODE_FIFO_KHR)) KHRSurface.VK_PRESENT_MODE_FIFO_KHR
      else modes.head

    val swapChainExtent = VkExtent2D.malloc(stack)
    if(surfCapabilities.currentExtent().width() == 0xFFFFFFFF && surfCapabilities.currentExtent().height() == 0xFFFFFFFF){
      // Surface size undefined. Set to the window size if within bounds
      val s = device.engine.size
      val minExt = surfCapabilities.minImageExtent()
      val maxExt = surfCapabilities.maxImageExtent()

      def minMax(v: Int, min: Int, max: Int) = v.min(max).max(min)

      swapChainExtent.width( minMax(s.width, minExt.width(), maxExt.width()) )
      swapChainExtent.height( minMax(s.height, minExt.height(), maxExt.height()) )
    }else{
      swapChainExtent.set(surfCapabilities.currentExtent())
    }

    val swapChainInfo = VkSwapchainCreateInfoKHR.calloc(stack)
      .sType$Default()
      .surface(surface.vkSurface)
      .minImageCount(imgCnt)
      .imageFormat(imageFormat)
      .imageColorSpace(colorSpace)
      .presentMode(presentMode)
      .imageExtent(swapChainExtent)
      .imageArrayLayers(1)
      .imageUsage(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
      .compositeAlpha(KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR)
      .preTransform(surfCapabilities.currentTransform())
      .clipped(true)

    val indices = device.queuesFamilies.toSet
    if(indices.size > 1){
      val intBuffer = stack.callocInt(indices.size)
      for(i <- indices) intBuffer.put(i)
      intBuffer.flip()

      swapChainInfo.imageSharingMode(VK10.VK_SHARING_MODE_CONCURRENT)
        .queueFamilyIndexCount(indices.size)
        .pQueueFamilyIndices(intBuffer)
    }else{
      swapChainInfo.imageSharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE)
    }

    val lp = stack.callocLong(1)
    vkCheck(KHRSwapchain.vkCreateSwapchainKHR(device.vkDevice, swapChainInfo, null, lp))
    val swapChain = lp.get(0)

    swapChain
  }

}

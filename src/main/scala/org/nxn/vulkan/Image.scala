package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkExtent3D, VkImageCreateInfo, VkImageFormatProperties, VkMemoryRequirements}
import org.nxn.utils.Dimension
import org.nxn.utils.Using.*

import java.util.function.Consumer

object Image{

  def depth(format:Int):Int = {
    format match {
      case VK10.VK_FORMAT_R8G8B8A8_SRGB => 4
    }
  }

}

class Image(device: Device, size:Dimension, reqMask:Int,
              format:Int = VK10.VK_FORMAT_R8G8B8A8_SRGB, mipLevels:Int = 1) extends Memory(device, size.size(Image.depth(format))){

  protected def initInage(size:Dimension, format:Int, mipLevels:Int):Long = MemoryStack.stackPush() | { stack =>
    val dev = device.physicalDevice

    val props = VkImageFormatProperties.calloc(stack)
    vkCheck(VK10.vkGetPhysicalDeviceImageFormatProperties(dev.vkPhysicalDevice, format, VK10.VK_IMAGE_TYPE_2D, VK10.VK_IMAGE_TILING_OPTIMAL, VK10.VK_IMAGE_USAGE_SAMPLED_BIT, 0, props))

    val ext = new Consumer[VkExtent3D]{
      override def accept(e: VkExtent3D): Unit = {
        e.width(size.width).height(size.height).depth(1)
      }
    }

    val info = VkImageCreateInfo.calloc(stack)
      .sType$Default()
      .imageType(VK10.VK_IMAGE_TYPE_2D)
      .tiling(VK10.VK_IMAGE_TILING_OPTIMAL)
      .initialLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)
      .usage(VK10.VK_IMAGE_USAGE_SAMPLED_BIT)
      .sharingMode(if(dev.graphicsQueueIndex == dev.presentQueueIndex) VK10.VK_SHARING_MODE_EXCLUSIVE else VK10.VK_SHARING_MODE_CONCURRENT)
      .extent(ext)
      .format(format)
      .arrayLayers(1)
      .mipLevels(mipLevels)
      .samples(VK10.VK_SAMPLE_COUNT_1_BIT)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateImage(device.vkDevice, info, null, lp))
    lp.get(0)
  }

  val vkImage:Long = initInage(size, format, mipLevels)

  override val vkMemory:Long = initMemory(reqMask)(new MemoryCallback{
    override def requirements(memReq: VkMemoryRequirements): Unit = {
      VK10.vkGetImageMemoryRequirements(device.vkDevice, vkImage, memReq)
    }
    override def bind(mem: Long): Unit = {
      vkCheck(VK10.vkBindImageMemory(device.vkDevice, vkImage, mem, 0))
    }
  })

  protected def initImageView(format:Int):ImageView = {
    new ImageView(device, vkImage, format)
  }

  val imageView: ImageView = initImageView(format)

  override def close(): Unit = {
    imageView.close()
    VK10.vkDestroyImage(device.vkDevice, vkImage, null)
    super.close()
  }

}

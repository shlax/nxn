package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkDescriptorImageInfo, VkExtent3D, VkImageCreateInfo, VkImageFormatProperties, VkImageMemoryBarrier, VkImageSubresourceRange, VkMemoryRequirements, VkWriteDescriptorSet}
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
              format:Int = VK10.VK_FORMAT_R8G8B8A8_SRGB) extends Memory(device, size.size(Image.depth(format))){

  protected def initInage(size:Dimension, format:Int):Long = MemoryStack.stackPush() | { stack =>
    val dev = device.physicalDevice

    val props = VkImageFormatProperties.calloc(stack)
    vkCheck(VK10.vkGetPhysicalDeviceImageFormatProperties(dev.vkPhysicalDevice, format, VK10.VK_IMAGE_TYPE_2D, VK10.VK_IMAGE_TILING_OPTIMAL, VK10.VK_IMAGE_USAGE_SAMPLED_BIT, 0, props))

    val info = VkImageCreateInfo.calloc(stack)
      .sType$Default()
      .imageType(VK10.VK_IMAGE_TYPE_2D)
      .tiling(VK10.VK_IMAGE_TILING_LINEAR)
      .initialLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)
      .usage(VK10.VK_IMAGE_USAGE_SAMPLED_BIT)
      .sharingMode(if(dev.graphicsQueueIndex == dev.presentQueueIndex) VK10.VK_SHARING_MODE_EXCLUSIVE else VK10.VK_SHARING_MODE_CONCURRENT)
      .extent(((e: VkExtent3D) => {
          e.width(size.width).height(size.height).depth(1)
        }):Consumer[VkExtent3D])
      .format(format)
      .arrayLayers(1)
      .mipLevels(1)
      .samples(VK10.VK_SAMPLE_COUNT_1_BIT)

    val lp = stack.callocLong(1)
    vkCheck(VK10.vkCreateImage(device.vkDevice, info, null, lp))
    lp.get(0)
  }

  val vkImage:Long = initInage(size, format)

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

  def toShaderLayout(commandPool: CommandPool, graphicsQueue:Queue, fence: Fence): Unit = {
    MemoryStack.stackPush() | { stack =>

      new CommandBuffer(commandPool) | { buff =>
        buff.record(stack, true)({ rec =>
          val barrier = VkImageMemoryBarrier.calloc(1, stack)
          barrier.get(0)
            .sType$Default()
            .oldLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)
            .newLayout(VK10.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
            .srcQueueFamilyIndex(VK10.VK_QUEUE_FAMILY_IGNORED)
            .dstQueueFamilyIndex(VK10.VK_QUEUE_FAMILY_IGNORED)
            .image(vkImage)
            .subresourceRange(((t: VkImageSubresourceRange) => {
              t.aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT)
                .baseMipLevel(0)
                .levelCount(1)
                .baseArrayLayer(0)
                .layerCount(1)
            }):Consumer[VkImageSubresourceRange])

          VK10.vkCmdPipelineBarrier(buff.vkCommandBuffer, VK10.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT, VK10.VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT, 0, null, null, barrier)
        })

        graphicsQueue.submit(buff, fence)
        fence.await()

      }

    }
  }

  def updateDescriptorSet(descriptorSet: DescriptorSet, binding:Int, sampler: Sampler):this.type = MemoryStack.stackPush() | { stack =>
    val imgInfo = VkDescriptorImageInfo.calloc(1, stack)
    imgInfo.get(0)
      .imageLayout(VK10.VK_IMAGE_LAYOUT_SHADER_READ_ONLY_OPTIMAL)
      .imageView(imageView.vkImageView)
      .sampler(sampler.vkSampler)

    val write = VkWriteDescriptorSet.calloc(1, stack)
    write.get(0)
      .sType$Default()
      .dstSet(descriptorSet.vkDescriptorSet)
      .dstBinding(binding)
      .descriptorType(VK10.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER)
      .descriptorCount(1)
      .pImageInfo(imgInfo)

    VK10.vkUpdateDescriptorSets(device.vkDevice, write, null)

    this
  }

  override def close(): Unit = {
    imageView.close()
    VK10.vkDestroyImage(device.vkDevice, vkImage, null)
    super.close()
  }

}

package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkFenceCreateInfo}
import scala.concurrent.duration.Duration
import org.nxn.Extensions.*

class GpFence(val device: GpDevice, val signaled:Boolean = true) extends AutoCloseable{

  private val vkDevice = device.vkDevice

  protected def init():Long = MemoryStack.stackPush() | { stack =>
    val info = VkFenceCreateInfo.calloc(stack)
      .sType$Default()
      .flags(if(signaled) VK10.VK_FENCE_CREATE_SIGNALED_BIT else 0)

    val buff = stack.callocLong(1)
    vkCheck(VK10.vkCreateFence(vkDevice, info, null, buff))
    buff.get(0)
  }

  val vkFence:Long = init()

  def await(waitAll:Boolean = true, timeout:Duration = device.physicalDevice.instance.system.timeout):Unit = {
    vkCheck( VK10.vkWaitForFences(vkDevice, vkFence, waitAll, timeout.toNanos) )
  }

  def reset():Unit = {
    vkCheck( VK10.vkResetFences(vkDevice, vkFence) )
  }

  override def close(): Unit = {
    VK10.vkDestroyFence(vkDevice, vkFence, null)
  }
}

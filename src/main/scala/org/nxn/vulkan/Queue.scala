package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkQueue, VkSubmitInfo}
import org.nxn.utils.Using.*

class Queue(val device:Device, val familyIndex:Int, val index:Int) {

  if(familyIndex >= device.queuesFamilies.size){
    throw new IndexOutOfBoundsException(familyIndex)
  }

  protected def initQueue(): VkQueue = MemoryStack.stackPush() | { stack =>
    val p = stack.callocPointer(1)
    VK10.vkGetDeviceQueue(device.vkDevice, familyIndex, index, p)
    new VkQueue(p.get(0), device.vkDevice)
  }

  val vkQueue:VkQueue = initQueue()

  def submit(buffer: CommandBuffer, signalFence: Fence): Unit = MemoryStack.stackPush() | { stack =>

    val pb = stack.pointers(buffer.vkCommandBuffer)

    val info = VkSubmitInfo.calloc(stack)
      .sType$Default()
      .pCommandBuffers(pb)
      .waitSemaphoreCount(0)
      .pSignalSemaphores(null)

    vkCheck(VK10.vkQueueSubmit(vkQueue, info, signalFence.vkFence))
  }

  def submit(buffer: CommandBuffer, waitSemaphore: Semaphore, stageMasks: Int,
             signalSemaphore: Semaphore, signalFence: Fence): Unit = MemoryStack.stackPush() | { stack =>

    val pb = stack.pointers(buffer.vkCommandBuffer)
    val ss = stack.longs(signalSemaphore.vkSemaphore)
    val mi = stack.ints(stageMasks)

    val info = VkSubmitInfo.calloc(stack)
      .sType$Default()
      .pCommandBuffers(pb)
      .pSignalSemaphores(ss)
      .pWaitDstStageMask(mi)

    val vs = stack.longs(waitSemaphore.vkSemaphore)
    info.waitSemaphoreCount(1).pWaitSemaphores(vs)

    vkCheck(VK10.vkQueueSubmit(vkQueue, info, signalFence.vkFence))
  }

}

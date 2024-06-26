package org.nxn.vulkan

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.{VK10, VkQueue, VkSubmitInfo}
import org.nxn.Extensions.*

class VnQueue(val device:VnDevice, val familyIndex:Int, val index:Int) {

  if(familyIndex >= device.queuesFamilies.size){
    throw new IndexOutOfBoundsException(familyIndex)
  }

  protected def initQueue(): VkQueue = MemoryStack.stackPush() | { stack =>
    val p = stack.callocPointer(1)
    VK10.vkGetDeviceQueue(device.vkDevice, familyIndex, index, p)
    new VkQueue(p.get(0), device.vkDevice)
  }

  val vkQueue:VkQueue = initQueue()

  def submit(buffer: VnCommandBuffer, waitSemaphore: VnSemaphore, signalSemaphore: VnSemaphore, stageMasks: Int, fence: VnFence): Unit = MemoryStack.stackPush() | { stack =>
    val pb = stack.pointers(buffer.vkCommandBuffer)
    val vs = stack.longs(waitSemaphore.vkSemaphore)
    val ss = stack.longs(signalSemaphore.vkSemaphore)
    val mi = stack.ints(stageMasks)

    val info = VkSubmitInfo.calloc(stack)
      .sType$Default()
      .pCommandBuffers(pb)
      .pSignalSemaphores(ss)
      .waitSemaphoreCount(1)
      .pWaitSemaphores(vs)
      .pWaitDstStageMask(mi)

    vkCheck(VK10.vkQueueSubmit(vkQueue, info, fence.vkFence))
  }

}

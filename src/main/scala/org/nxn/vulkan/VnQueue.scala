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

  def submit(buffer: VnCommandBuffer, signalSemaphore: VnSemaphore, stageMasks: Int,
             waitSemaphore: Option[VnSemaphore] = None, fence: Option[VnFence] = None): Unit = MemoryStack.stackPush() | { stack =>

    val pb = stack.pointers(buffer.vkCommandBuffer)
    val ss = stack.longs(signalSemaphore.vkSemaphore)
    val mi = stack.ints(stageMasks)

    val info = VkSubmitInfo.calloc(stack)
      .sType$Default()
      .pCommandBuffers(pb)
      .pSignalSemaphores(ss)
      .pWaitDstStageMask(mi)

    if(waitSemaphore.isDefined){
      val vs = stack.longs(waitSemaphore.get.vkSemaphore)
      info.waitSemaphoreCount(1).pWaitSemaphores(vs)
    }else{
      info.waitSemaphoreCount(0)
    }

    val f = fence.map(_.vkFence).getOrElse(VK10.VK_NULL_HANDLE)

    vkCheck(VK10.vkQueueSubmit(vkQueue, info, f))
  }

}

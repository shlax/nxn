package org.nxn

import org.lwjgl.system.MemoryStack
import org.lwjgl.vulkan.VK10
import org.nxn.*

class NxnPhysicalDevice(instance: NxnInstance) {


  MemoryStack.stackPush()|{ stack =>
    val nBuff = stack.callocInt(1)
    vkCheck(VK10.vkEnumeratePhysicalDevices(instance.vkInstance, nBuff, null))
    val n = nBuff.get(0)
    if(n <= 0){
      throw new IllegalStateException("vkEnumeratePhysicalDevices reported zero accessible devices.")
    }

    val devices = stack.mallocPointer(n)
    vkCheck(VK10.vkEnumeratePhysicalDevices(instance.vkInstance, nBuff, devices))
    for(i <- 0 until n){

    }

  }

}

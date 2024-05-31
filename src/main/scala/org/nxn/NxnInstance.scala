package org.nxn

import org.lwjgl.PointerBuffer
import org.lwjgl.glfw.GLFWVulkan
import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.lwjgl.vulkan.{EXTDebugUtils, VK, VK10, VkApplicationInfo, VkDebugUtilsMessengerCallbackDataEXT, VkDebugUtilsMessengerCallbackEXT, VkDebugUtilsMessengerCallbackEXTI, VkDebugUtilsMessengerCreateInfoEXT, VkExtensionProperties, VkInstance, VkInstanceCreateInfo, VkLayerProperties}
import org.nxn.*

class NxnInstance(ctx: NxnEngine) extends AutoCloseable, VkDebugUtilsMessengerCallbackEXTI, NxnContext{
  override val engine: NxnEngine = ctx

  private var dbgFn:Option[VkDebugUtilsMessengerCallbackEXT] = None
  private var dbgCallBack:Option[Long] = None

  val vkInstance:VkInstance = MemoryStack.stackPush()|{ stack =>
    var requiredLayers:Option[PointerBuffer] = None
    if(ctx.debug){ // validate layer
      val nBuff = stack.callocInt(1)
      vkCheck(VK10.vkEnumerateInstanceLayerProperties(nBuff, null))
      val n = nBuff.get(0)
      if(n > 0) {
        val availableLayers = VkLayerProperties.calloc(n, stack)
        vkCheck(VK10.vkEnumerateInstanceLayerProperties(nBuff, availableLayers))
        for (i <- 0 until n) {
          val l = availableLayers.get(i)
          if (l.layerNameString() == "VK_LAYER_KHRONOS_validation") {
            val p = stack.callocPointer(1)
            p.put(0, stack.ASCII("VK_LAYER_KHRONOS_validation"))
            requiredLayers = Some(p)
          }
        }
      }
    }

    val glfwExt = GLFWVulkan.glfwGetRequiredInstanceExtensions()
    if(glfwExt == null){
      throw new IllegalStateException("glfwGetRequiredInstanceExtensions failed to find the platform surface extensions.");
    }

    var requiredExtension:Option[PointerBuffer] = None
    if(ctx.debug) {
      val nBuff = stack.callocInt(1)
      vkCheck(VK10.vkEnumerateInstanceExtensionProperties(null.asInstanceOf[CharSequence], nBuff, null))
      val n = nBuff.get(0)
      if (n > 0) {
        val ext = VkExtensionProperties.calloc(n, stack)
        vkCheck(VK10.vkEnumerateInstanceExtensionProperties(null.asInstanceOf[CharSequence], nBuff, ext))
        for(i <- 0 until n){
          val e = ext.get(i)
          if(EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME == e.extensionNameString()){
            val p = stack.callocPointer(1)
            p.put(stack.ASCII(EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME))
            requiredExtension = Some(p)
          }
        }
      }
    }

    val nm = stack.UTF8(ctx.name)
    val appInfo = VkApplicationInfo.calloc(stack)
      .sType$Default()
      .pNext(MemoryUtil.NULL)
      .pApplicationName(nm)
      .applicationVersion(1)
      .pEngineName(nm)
      .engineVersion(0)
      .apiVersion(VK.getInstanceVersionSupported)

    val ext = if(requiredExtension.isDefined){
      val p = stack.callocPointer(glfwExt.remaining() + 1)
      p.putBuffer(glfwExt)
      p.putBuffer(requiredExtension.get)
      p.flip()
    }else{
      val p = stack.callocPointer(glfwExt.remaining())
      p.putBuffer(glfwExt)
      p.flip()
    }

    val iInfo = VkInstanceCreateInfo.calloc(stack)
      .sType$Default()
      .pApplicationInfo(appInfo)
      .ppEnabledLayerNames(requiredLayers.orNull)
      .ppEnabledExtensionNames(ext)
      .flags(0)

    var dbgInfo:Option[VkDebugUtilsMessengerCreateInfoEXT] = None
    if(ctx.debug){
      val f = VkDebugUtilsMessengerCallbackEXT.create(this)
      dbgFn = Some(f)

      val dbgInf = VkDebugUtilsMessengerCreateInfoEXT.calloc(stack)
        .sType$Default()
        .messageSeverity(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT
          | EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT)
        .messageType(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT
          | EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT
          | EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT)
        .pfnUserCallback(f)
        .pUserData(MemoryUtil.NULL)
        .pNext(0)
        .flags(0)

      dbgInfo = Some(dbgInf)

      iInfo.pNext(dbgInf.address())
    }else{
      iInfo.pNext(MemoryUtil.NULL)
    }

    val p = stack.callocPointer(1)
    vkCheck(VK10.vkCreateInstance(iInfo, null, p))

    val vkInst = new VkInstance(p.get(0), iInfo)

    for(i <- dbgInfo){
      val lp = stack.callocLong(1)
      vkCheck(EXTDebugUtils.vkCreateDebugUtilsMessengerEXT(vkInst, i, null, lp))
      dbgCallBack = Some(lp.get(0))
    }

    vkInst
  }

  override def invoke(messageSeverity: Int, messageTypes: Int, pCallbackData: Long, pUserData: Long): Int = {

    val severity = if((messageSeverity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_VERBOSE_BIT_EXT) != 0) "VERBOSE"
      else if((messageSeverity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_INFO_BIT_EXT) != 0) "INFO"
      else if((messageSeverity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT) != 0) "WARNING"
      else if((messageSeverity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT) != 0) "ERROR"
      else  "UNKNOWN"

    val tpe = if((messageTypes & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT) != 0) "GENERAL"
      else if((messageTypes & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT) != 0) "VALIDATION"
      else if((messageTypes & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT) != 0) "PERFORMANCE"
      else "UNKNOWN"

    val data = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData)
    println("["+tpe+":"+severity+"]"+data.pMessageIdNameString()+":"+data.pMessageString())

    VK10.VK_FALSE
  }

  override def close(): Unit = {
    for(c <- dbgCallBack) EXTDebugUtils.vkDestroyDebugUtilsMessengerEXT(vkInstance, c, null)
    VK10.vkDestroyInstance(vkInstance, null)
    for(i <- dbgFn) i.free()
  }

}

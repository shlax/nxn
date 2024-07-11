package org.nxn

import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.lwjgl.util.shaderc.Shaderc
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkDescriptorSetLayoutBinding, VkDescriptorSetLayoutCreateInfo, VkPipelineLayoutCreateInfo, VkPipelineVertexInputStateCreateInfo, VkVertexInputAttributeDescription, VkVertexInputBindingDescription}
import org.nxn.utils.Using.*
import org.nxn.utils.{Dimension, FpsCounter}
import org.nxn.vulkan.memory.MemoryBuffer
import org.nxn.vulkan.shader.ShaderCompiler
import org.nxn.vulkan.{Buffer, DescriptorPool, Fence, Pipeline, RenderCommand, Semaphore, TypeLength, VulkanSystem, vkCheck}

object Main extends Runnable{

  def main(args:Array[String]) : Unit = {
    try {
      run()
    }catch {
      case e: Throwable =>
        e.printStackTrace()
    }
  }

  override def run(): Unit = {
    val fps = new FpsCounter()

    val shaders = new ShaderCompiler() | { comp =>
      IndexedSeq(
        comp.compile("/shaders/shader.vert", Shaderc.shaderc_glsl_vertex_shader, VK10.VK_SHADER_STAGE_VERTEX_BIT),
        comp.compile("/shaders/shader.frag", Shaderc.shaderc_glsl_fragment_shader, VK10.VK_SHADER_STAGE_FRAGMENT_BIT)
      )
    }

    new VulkanSystem("NXN", Dimension(1280, 720)) | { sys =>
      val graphicsQueue = sys.device.graphicsQueue
      val presentQueue = sys.device.presentQueue

      using { use =>
        val imageAvailableSemaphore = use(new Semaphore(sys.device))
        val renderFinishedSemaphore = use(new Semaphore(sys.device))
        val inFlightFence = use(new Fence(sys.device))

        // vec2(0.0, -0.5), vec2(-0.5, 0.5), vec2(0.5, 0.5)
        val points = use(new Buffer(sys.device, 3 * 2 * TypeLength.floatLength.size, VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT,
            VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).map((memory: MemoryBuffer) => {
          val b = MemoryUtil.memFloatBuffer(memory.address, memory.size)

          def vec2(x:Float, y:Float):Unit = {
            b.put(x).put(y)
          }

          vec2(0.0, -0.5)
          vec2(-0.5, 0.5)
          vec2(0.5, 0.5)
        })

        val indexes = use(new Buffer(sys.device, 3 * TypeLength.intLength.size, VK10.VK_BUFFER_USAGE_INDEX_BUFFER_BIT,
          VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).map((memory: MemoryBuffer) => {
          val b = MemoryUtil.memIntBuffer(memory.address, memory.size)
          b.put(0).put(1).put(2)
        })

        /* layout(binding = 0) uniform UniformBufferObject{
            mat4 viewMatrix;
          } ubo;

        val descPool = use(new DescriptorPool(sys.device, Map(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER -> 1)))

        val uniform = use(new Buffer(sys.device, 4 * 4 * TypeLength.floatLength.size, VK10.VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT,
          VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).map((memory: MemoryBuffer) => {
          val b = MemoryUtil.memFloatBuffer(memory.address, memory.size)
          b.put(1f).put(0f).put(0f).put(1f)
          b.put(0f).put(1f).put(0f).put(1f)
          b.put(0f).put(0f).put(1f).put(1f)
          b.put(0f).put(0f).put(0f).put(1f)
        }) */

        val triangle = use(new Pipeline(sys.renderPass, shaders){

          override protected def pipelineLayout(stack: MemoryStack, info: VkPipelineLayoutCreateInfo): Unit = {
            val desc = VkDescriptorSetLayoutBinding.calloc(1, stack)
            desc.get(0)
              .descriptorType(VK10.VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER)
              .stageFlags(VK10.VK_SHADER_STAGE_VERTEX_BIT)
              .descriptorCount(1)
              .binding(0)

            val layoutInfo = VkDescriptorSetLayoutCreateInfo.calloc(stack)
              .sType$Default()
              .pBindings(desc)

            val descriptorSetLayout = stack.callocLong(1)
            vkCheck(VK10.vkCreateDescriptorSetLayout(renderPass.swapChain.device.vkDevice, layoutInfo, null, descriptorSetLayout))
            descriptorSetLayout.flip()

            info.pSetLayouts(descriptorSetLayout)
          }

          // layout(location = 0) in vec2 inPosition
          override protected def vertexInput(stack: MemoryStack, info:VkPipelineVertexInputStateCreateInfo):Unit = {
            val bindings = VkVertexInputBindingDescription.calloc(1, stack)
            bindings.get(0)
              .binding(0)
              .stride(2 * TypeLength.floatLength.size)
              .inputRate(VK10.VK_VERTEX_INPUT_RATE_VERTEX)
            info.pVertexBindingDescriptions(bindings)

            val attributes = VkVertexInputAttributeDescription.calloc(1, stack)
            attributes.get(0)
              .binding(0)
              .location(0)
              .format(VK10.VK_FORMAT_R32G32_SFLOAT)
              .offset(0)
            info.pVertexAttributeDescriptions(attributes)
          }
        })

        new RenderCommand(sys.renderPass) | { render =>
          // >>
          while (sys.window.pullEvents()) {
            inFlightFence.await().reset()

            val next = sys.swapChain.acquireNextImage(imageAvailableSemaphore) // waiting
            for(q <- next.presentResult) println(q)

            val cmdBuff = render.record(next)((stack:MemoryStack, buff: VkCommandBuffer) => {
              triangle.bindPipeline(buff)

              VK10.vkCmdBindVertexBuffers(buff, 0, stack.longs(points.buffer), stack.longs(0L))
              VK10.vkCmdBindIndexBuffer(buff, indexes.buffer, 0, VK10.VK_INDEX_TYPE_UINT32) //VK10.vkCmdDraw(buff, 3, 1, 0, 0)
              VK10.vkCmdDrawIndexed(buff, 3, 1, 0, 0, 0)
            })

            graphicsQueue.submit(cmdBuff, imageAvailableSemaphore, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT, renderFinishedSemaphore, Some(inFlightFence))
            //graphicsQueue.submit(cmdBuff, imageAvailableSemaphore, VK10.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT, renderFinishedSemaphore, Some(inFlightFence))
            val res = sys.swapChain.presentImage(presentQueue, next, renderFinishedSemaphore)
            for(q <- res) println(q)

            fps(f => println("fps: "+f))
          }

          sys.device.await()
          // <<

        }

      }
    }
  }

}

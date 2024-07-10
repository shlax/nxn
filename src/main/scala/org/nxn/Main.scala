package org.nxn

import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.lwjgl.util.shaderc.Shaderc
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkPipelineVertexInputStateCreateInfo, VkVertexInputAttributeDescription, VkVertexInputBindingDescription}
import org.nxn.utils.Using.*
import org.nxn.utils.{Dimension, FpsCounter}
import org.nxn.vulkan.shader.ShaderCompiler
import org.nxn.vulkan.{Constants, VnBuffer, VnFence, VnPipeline, VnRenderCommand, VnSemaphore, VnSystem}

import java.util.function.{BiConsumer, Consumer}

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

    new VnSystem("NXN", Dimension(1280, 720)) | { sys =>
      val graphicsQueue = sys.device.graphicsQueue
      val presentQueue = sys.device.presentQueue

      using { use =>
        val imageAvailableSemaphore = use(new VnSemaphore(sys.device))
        val renderFinishedSemaphore = use(new VnSemaphore(sys.device))
        val inFlightFence = use(new VnFence(sys.device))

        // vec2(0.0, -0.5), vec2(-0.5, 0.5), vec2(0.5, 0.5)
        val points = use(new VnBuffer(sys.device, 3 * 2 * Constants.floatLength, VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT,
          VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).map((t: MemoryStack, memory: VnBuffer#MemoryBuffer) => {
          val b = MemoryUtil.memFloatBuffer(memory.address, memory.capacity)

          def vec2(x:Float, y:Float):Unit = {
            b.put(x).put(y)
          }

          vec2(0.0, -0.5)
          vec2(-0.5, 0.5)
          vec2(0.5, 0.5)
        })

        val triangle = use(new VnPipeline(sys.renderPass, shaders){
          // layout(location = 0) in vec2 inPosition
          override protected def vertexInput(stack: MemoryStack, info:VkPipelineVertexInputStateCreateInfo):Unit = {
            val bindings = VkVertexInputBindingDescription.calloc(1, stack)
            bindings.get(0)
              .binding(0)
              .stride(2 * Constants.floatLength)
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

        new VnRenderCommand(sys.renderPass) | { render =>
          // >>
          while (sys.window.pullEvents()) {
            inFlightFence.await().reset()

            val next = sys.swapChain.acquireNextImage(imageAvailableSemaphore)
            for(q <- next.presentResult) println(q)

            val cmdBuff = render.record(next)((stack:MemoryStack, buff: VkCommandBuffer) => {
              triangle.bindPipeline(buff)
              VK10.vkCmdBindVertexBuffers(buff, 0, stack.longs(points.buffer), stack.longs(0L))
              VK10.vkCmdDraw(buff, 3, 1, 0, 0)
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

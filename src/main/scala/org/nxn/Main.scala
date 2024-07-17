package org.nxn

import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.system.{MemoryStack, MemoryUtil}
import org.lwjgl.util.shaderc.Shaderc
import org.lwjgl.vulkan.{VK10, VkCommandBuffer, VkPipelineLayoutCreateInfo, VkPipelineVertexInputStateCreateInfo, VkPushConstantRange, VkVertexInputAttributeDescription, VkVertexInputBindingDescription}
import org.nxn.utils.Using.*
import org.nxn.utils.{Dimension, FpsCounter}
import org.nxn.vulkan.memory.MemoryBuffer
import org.nxn.vulkan.shader.ShaderCompiler
import org.nxn.vulkan.{Buffer, DescriptorPool, DescriptorSet, DescriptorSetLayout, Fence, Image, Pipeline, PipelineLayout, RenderCommand, Sampler, Semaphore, TypeLength, VulkanSystem}

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

      using { use =>
        val imageAvailableSemaphore = use(new Semaphore(sys.device))
        val renderFinishedSemaphore = use(new Semaphore(sys.device))
        val inFlightFence = use(new Fence(sys.device))

        // vec2(0.0, -0.5), vec2(-0.5, 0.5), vec2(0.5, 0.5)
        val points = use(new Buffer(sys.device, 2 * 3 * 3 * TypeLength.floatLength.size, VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT,
            VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).mapMemory{ memory =>
          val b = MemoryUtil.memFloatBuffer(memory.address, memory.size)

          def vec3(x:Float, y:Float, z:Float):Unit = {
            b.put(x).put(y).put(z)
          }

          vec3(-0.75, -0.75, 0.25)
          vec3( 0.75, -0.75, 0.25)
          vec3( 0.00,  0.75, 0.75)

          vec3(-0.75,  0.75, 0.25)
          vec3( 0.75,  0.75, 0.25)
          vec3( 0.00, -0.75, 0.75)
        }

        val indexes = use(new Buffer(sys.device, 6 * TypeLength.intLength.size, VK10.VK_BUFFER_USAGE_INDEX_BUFFER_BIT,
          VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT)).mapMemory{ memory =>
          val b = MemoryUtil.memIntBuffer(memory.address, memory.size)
          b.put(2).put(1).put(0)
          b.put(3).put(4).put(5)
        }

        val sampler = use(new Sampler(sys.device))

        val descriptorPool = use(new DescriptorPool(sys.device, Map(VK10.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER -> 1)))
        val layout = use(new DescriptorSetLayout(sys.device, 0, VK10.VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER, VK10.VK_SHADER_STAGE_FRAGMENT_BIT))

        val descriptorSet = use(new DescriptorSet(descriptorPool, IndexedSeq(layout)))

        // layout(push_constant) uniform Transformations { mat4 viewMatrix; } transformations;
        val pipelineLayout = use(new PipelineLayout(sys.device){
          override protected def pipelineLayout(stack: MemoryStack, info: VkPipelineLayoutCreateInfo): Unit = {
            val ranges = VkPushConstantRange.calloc(1, stack)
              .stageFlags(VK10.VK_SHADER_STAGE_VERTEX_BIT)
              .offset(0)
              .size(4 * 4 * TypeLength.floatLength.size)
            info.pPushConstantRanges(ranges)

            info.pSetLayouts(stack.longs(layout.vkDescriptorLayout))
          }
        })

        val triangle = use(new Pipeline(pipelineLayout, sys.renderPass, shaders){

          // layout(location = 0) in vec2 inPosition
          override protected def vertexInput(stack: MemoryStack, info:VkPipelineVertexInputStateCreateInfo):Unit = {
            val bindings = VkVertexInputBindingDescription.calloc(1, stack)
            bindings.get(0)
              .binding(0)
              .stride(3 * TypeLength.floatLength.size)
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

          val texture = use(new Image(sys.device, Dimension(512, 512))).updateDescriptorSet(descriptorSet, 0, sampler)

          new Fence(sys.device, false) | { fence =>
            new Buffer(sys.device, 512 * 512 * 4, VK10.VK_BUFFER_USAGE_TRANSFER_SRC_BIT,
              VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT | VK10.VK_MEMORY_PROPERTY_HOST_COHERENT_BIT) |{ stageBuffer =>

              stageBuffer.mapMemory{ memory =>
                val b = MemoryUtil.memByteBuffer(memory.address, memory.size)

                Main.getClass.getResourceAsStream("/textures/checker.png") | { is =>
                  val dec = new PNGDecoder(is)
                  dec.decode(b, 512 * 4, PNGDecoder.Format.RGBA)
                }
              }

              texture.copyBufferToImage(stageBuffer, render.commandPool, fence)|{ _ =>
                fence.await()
              }

            }
          }

          // >>
          while (sys.window.pullEvents()) {
            inFlightFence.await().reset()

            val next = sys.swapChain.acquireNextImage(imageAvailableSemaphore) // waiting
            for(q <- next.presentResult) println(q)

            val cmdBuff = render.record(next){ (stack, buff) =>
              triangle.bindPipeline(buff)

              val viewMatrix = stack.callocFloat(4 * 4)
              viewMatrix.put(1f).put(0f).put(0f).put(0f)
              viewMatrix.put(0f).put(1f).put(0f).put(0f)
              viewMatrix.put(0f).put(0f).put(1f).put(0f)
              viewMatrix.put(0f).put(0f).put(0f).put(1f)
              viewMatrix.flip()

              VK10.vkCmdPushConstants(buff, pipelineLayout.vkPipelineLayout, VK10.VK_SHADER_STAGE_VERTEX_BIT, 0, viewMatrix)

              VK10.vkCmdBindDescriptorSets(buff, VK10.VK_PIPELINE_BIND_POINT_GRAPHICS, pipelineLayout.vkPipelineLayout, 0, stack.longs(descriptorSet.vkDescriptorSet), null)

              VK10.vkCmdBindVertexBuffers(buff, 0, stack.longs(points.vkBuffer), stack.longs(0L))
              VK10.vkCmdBindIndexBuffer(buff, indexes.vkBuffer, 0, VK10.VK_INDEX_TYPE_UINT32) //VK10.vkCmdDraw(buff, 3, 1, 0, 0)
              VK10.vkCmdDrawIndexed(buff, 6, 1, 0, 0, 0)
            }

            graphicsQueue.submit(cmdBuff, imageAvailableSemaphore, VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT, renderFinishedSemaphore, inFlightFence)
            //graphicsQueue.submit(cmdBuff, imageAvailableSemaphore, VK10.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT, renderFinishedSemaphore, Some(inFlightFence))
            val res = sys.swapChain.presentImage(next, renderFinishedSemaphore)
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

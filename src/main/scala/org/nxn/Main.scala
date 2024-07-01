package org.nxn

import org.lwjgl.util.shaderc.Shaderc
import org.lwjgl.vulkan.{VK10, VkCommandBuffer}
import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.shader.ShaderCompiler
import org.nxn.vulkan.{VnPipeline, VnRenderCommand, VnSystem}

import java.util.function.Consumer
import scala.util.control.NonFatal

object Main {

  def main(args:Array[String]) : Unit = {
    try {
      val shaders = new ShaderCompiler() |{ comp => IndexedSeq(
          comp.compile("/shaders/shader.vert", Shaderc.shaderc_glsl_vertex_shader, VK10.VK_SHADER_STAGE_VERTEX_BIT),
          comp.compile("/shaders/shader.frag", Shaderc.shaderc_glsl_fragment_shader, VK10.VK_SHADER_STAGE_FRAGMENT_BIT)
        ) }

      new VnSystem(true, "NXN", Dimension(1280, 720)) | { sys =>
        val graphicsQueue = sys.device.graphicsQueue
        val presentQueue = sys.device.presentQueue

        new VnPipeline(sys.renderPass, shaders)|{ pipeline =>
          new VnRenderCommand(sys.renderPass)((buff: VkCommandBuffer) => {
            pipeline.bindPipeline(buff)
            VK10.vkCmdDraw(buff, 3, 1, 0, 0)
          }) | { render =>
            // >>
            while ( sys.window.pullEvents()) {

            }
            // <<
          }
        }

      }
    }catch {
      case NonFatal(e) =>
        e.printStackTrace()
    }
  }

}

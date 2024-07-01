package org.nxn

import org.lwjgl.util.shaderc.Shaderc
import org.lwjgl.vulkan.{VK10, VkCommandBuffer}
import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.shader.ShaderCompiler
import org.nxn.vulkan.{VnDevice, VnInstance, VnPhysicalDevice, VnPipeline, VnRenderCommand, VnRenderPass, VnSurface, VnSwapChain, VnSystem, VnWindow}

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
        new VnWindow(sys) | { win =>
          new VnInstance(sys) | { inst =>
            new VnSurface(inst, win) |{ surf =>
              val psDev = new VnPhysicalDevice(inst, surf)
              new VnDevice(psDev) | { dev =>
                val graphicsQueue = dev.graphicsQueue
                val presentQueue = dev.presentQueue
                new VnSwapChain(surf, dev)|{ swap =>
                  new VnRenderPass(swap)| { renderPass =>
                    new VnPipeline(renderPass, shaders)|{ pipeline =>
                      new VnRenderCommand(renderPass)((buff: VkCommandBuffer) => {
                        pipeline.bindPipeline(buff)
                        VK10.vkCmdDraw(buff, 3, 1, 0, 0)
                      }) | { render =>
                        // >>
                        while (win.pullEvents()) {

                        }
                        // <<
                      }

                    }
                  }
                }

              }
            }
          }
        }
      }
    }catch {
      case NonFatal(e) =>
        e.printStackTrace()
    }
  }

}

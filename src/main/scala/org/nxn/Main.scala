package org.nxn

import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.{GpDevice, GpInstance, GpPhysicalDevice, GpQueue, GpSurface, GpSwapChain, GpSystem, GpWindow}

import scala.util.control.NonFatal

object Main {

  def main(args:Array[String]) : Unit = {
    try {
      new GpSystem(true, "NXN", Dimension(1280, 720)) | { sys =>
        new GpWindow(sys) | { win =>
          new GpInstance(sys) | { inst =>
            new GpSurface(inst, win) |{ surf =>
              val psDev = new GpPhysicalDevice(inst, surf)
              new GpDevice(psDev) | { dev =>
                val graphicsQueue = dev.graphicsQueue
                val presentQueue = dev.presentQueue
                new GpSwapChain(surf, dev)|{ swap =>

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
    }catch {
      case NonFatal(e) =>
        e.printStackTrace()
    }
  }

}

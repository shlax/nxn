package org.nxn

import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.{ViDevice, ViInstance, ViPhysicalDevice, ViQueue, ViSurface, ViSwapChain, ViSystem, ViWindow}

import scala.util.control.NonFatal

object Main {

  def main(args:Array[String]) : Unit = {
    try {
      new ViSystem(true, "NXN", Dimension(1280, 720)) | { sys =>
        new ViWindow(sys) | { win =>
          new ViInstance(sys) | { inst =>
            new ViSurface(inst, win) |{ surf =>
              val psDev = new ViPhysicalDevice(inst, surf)
              new ViDevice(psDev) | { dev =>
                val graphicsQueue = dev.graphicsQueue
                val presentQueue = dev.presentQueue
                new ViSwapChain(surf, dev)|{ swap =>

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

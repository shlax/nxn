package org.nxn

import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.{VnDevice, VnInstance, VnPhysicalDevice, VnQueue, VnSurface, VnSwapChain, VnSystem, VnWindow}

import scala.util.control.NonFatal

object Main {

  def main(args:Array[String]) : Unit = {
    try {
      new VnSystem(true, "NXN", Dimension(1280, 720)) | { sys =>
        new VnWindow(sys) | { win =>
          new VnInstance(sys) | { inst =>
            new VnSurface(inst, win) |{ surf =>
              val psDev = new VnPhysicalDevice(inst, surf)
              new VnDevice(psDev) | { dev =>
                val graphicsQueue = dev.graphicsQueue
                val presentQueue = dev.presentQueue
                new VnSwapChain(surf, dev)|{ swap =>

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

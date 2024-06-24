package org.nxn

import org.nxn.Extensions.*
import org.nxn.utils.Dimension
import org.nxn.vulkan.{GeDevice, GeInstance, GePhysicalDevice, GeSurface, GeSystem, GeWindow}

import scala.util.control.NonFatal

object Main {

  def main(args:Array[String]) : Unit = {
    try {
      new GeSystem(true, "NXN", Dimension(1280, 720)) | { sys =>
        new GeWindow(sys) | { win =>
          new GeInstance(sys) | { inst =>
            new GeSurface(inst, win) |{ surf =>
              val psDev = new GePhysicalDevice(inst, surf)
              new GeDevice(psDev) | { dev =>
                // >>
                while (win.pullEvents()) {

                }
                // <<
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

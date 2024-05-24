package org

import org.lwjgl.PointerBuffer
import org.lwjgl.system.CustomBuffer

import scala.annotation.targetName

package object nxn {

  extension [T <: AutoCloseable] (ac:T){
    @targetName("withClose")
    inline def | [R](f : T => R ):R = {
      try{
        f.apply(ac)
      }finally {
        ac.close()
      }
    }
  }

  extension (a:PointerBuffer){
    inline def putBuffer(b:PointerBuffer):PointerBuffer = {
      a.asInstanceOf[CustomBuffer[PointerBuffer]].put(b)
    }
  }

}

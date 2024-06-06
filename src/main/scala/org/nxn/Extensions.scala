package org.nxn

import scala.annotation.targetName

object Extensions {

  extension [T <: AutoCloseable](ac: T) {
    @targetName("withClose")
    inline def |[R](f: T => R): R = {
      try {
        f.apply(ac)
      } finally {
        ac.close()
      }
    }
  }

}

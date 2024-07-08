package org.nxn.utils

import java.util.function.Consumer

class FpsCounter(mod:Int = 100) {

  private var started:Long = 0
  private var cnt:Long = -1

  def apply(c: Consumer[Double]): Unit = {
    if(cnt == -1){
      started = System.nanoTime()
      cnt = 0
    }else {
      cnt += 1
      if (cnt % mod == 0) {
        val d = System.nanoTime() - started
        val fps = cnt.toDouble / d.toDouble * 1000_000_000d
        c.accept(fps)
      }
    }

  }

  def reset():Unit = {
    started = 0
    cnt = -1
  }

}

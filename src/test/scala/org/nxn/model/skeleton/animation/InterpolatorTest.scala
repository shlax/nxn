package org.nxn.model.skeleton.animation

object InterpolatorTest {

  def main(args: Array[String]): Unit = {
    val d = Seq(1f, -1f, -1f, 0f, 1f, 1f, -1f, -1f)
    val i = new Interpolator

    def time(): Unit = {
      var t = 0.1f
      while (t < 1) {
        println(i.apply(t))
        t += 0.1f
      }
      println(i.apply(1f))
    }

    i.update(d(0), d(1))
    println(i.apply(0f))
    for(j <- 1 until d.length - 1){
      time()
      i.update(d(j), d(j+1))
    }
    time()
  }

}

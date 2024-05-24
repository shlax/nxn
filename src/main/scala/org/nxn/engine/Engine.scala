package org.nxn.engine

import java.util.concurrent.{ExecutorService, Executors, TimeUnit}

class Engine(logic:Logic) extends Runnable{

  @volatile
  private var running:Boolean = false
  def isRunning:Boolean = running

  override def run(): Unit = {

    logic.init()
    var time = System.nanoTime()

    while (running){
      val t = System.nanoTime()
      logic.update(t - time)
      time = t
    }

  }

  private var pull:Option[ExecutorService] = None

  def start():Unit = {
    running = true
    pull = Some( Executors.newSingleThreadExecutor() )
    pull.get.submit(this)
  }

  def stop(): Unit = {
    running = false
    val p = pull.get
    p.shutdown()
    while(! p.awaitTermination(1, TimeUnit.SECONDS) ){
      println("Engine didn't finish in 1 second")
    }
    pull = None
  }

}
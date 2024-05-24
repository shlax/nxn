package org.nxn.engine

trait Logic extends AutoCloseable{

  def init():Unit

  def update(diffNanoTime:Long):Unit

}

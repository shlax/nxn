package org.nxn.utils

case class Dimension(width:Int, height:Int){

  def size(depth: Int): Int = {
    width * height * depth
  }

}

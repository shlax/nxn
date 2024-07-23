package org.nxn.model

import org.nxn.math.{Vector2f, Vector3f}

class ParsedVertex(val index:Int, val normal:Vector3f, val uvs:Array[Vector2f]){

  def invert():this.type = {
    normal.y = -1 * normal.y
    this
  }

}

package org.nxn.model

import org.nxn.utils.Axis

class ParsedTriangle(val a:ParsedVertex, val b:ParsedVertex, val c:ParsedVertex){

  def invert(ax:Axis):this.type = {
    a.invert(ax); b.invert(ax); c.invert(ax)
    this
  }

}

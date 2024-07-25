package org.nxn.model

class ParsedTriangle(val a:ParsedVertex, val b:ParsedVertex, val c:ParsedVertex){

  def invert():this.type = {
    a.invert(); b.invert(); c.invert()
    this
  }

}

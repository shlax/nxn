package org.nxn.model

class ParsedTriangle(var a:ParsedVertex, var b:ParsedVertex, var c:ParsedVertex){

  def invert():this.type = {
    a.invert(); b.invert(); c.invert()
    val t = a;  a = c; c = t
    this
  }

}

package org.nxn.model

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.nxn.math.Vector3f
import org.nxn.model.parser.{ModelLexer, ModelParser}

import java.io.InputStream
import java.nio.charset.StandardCharsets

object ParsedModel{

  def load(in:InputStream): ParsedModel = {
    val p = new ModelParser(new CommonTokenStream(new ModelLexer(CharStreams.fromStream(in, StandardCharsets.UTF_8))))
    p.model().result
  }

}

class ParsedModel(val points:Array[Vector3f], val faces:Array[ParsedTriangle]){



}

package org.nxn.model.skeleton.animation

import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.nxn.model.ExceptionErrorListener
import org.nxn.model.skeleton.animation.parser.{KeyFrameParser, KeyFrameLexer}

import java.io.InputStream
import java.nio.charset.StandardCharsets

class KeyFrameLoader extends ExceptionErrorListener{

  def loadSkeleton(in: InputStream): ParsedKeyFrame = {
    val p = KeyFrameParser(CommonTokenStream(KeyFrameLexer(CharStreams.fromStream(in, StandardCharsets.UTF_8))))
    p.removeErrorListeners()
    p.addErrorListener(this)
    p.keyFrame().result
  }

}

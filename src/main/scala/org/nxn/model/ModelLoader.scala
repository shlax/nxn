package org.nxn.model

import org.antlr.v4.runtime.atn.ATNConfigSet
import org.antlr.v4.runtime.dfa.DFA
import org.antlr.v4.runtime.{ANTLRErrorListener, CharStreams, CommonTokenStream, Parser, RecognitionException, Recognizer}
import org.nxn.model.parser.{ModelLexer, ModelParser}

import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util

class ModelLoader extends ANTLRErrorListener{

  def loadModel(in: InputStream): ParsedModel = {
    val p = new ModelParser(new CommonTokenStream(new ModelLexer(CharStreams.fromStream(in, StandardCharsets.UTF_8))))
    p.removeErrorListeners()
    p.addErrorListener(this)
    p.model().result
  }

  override def syntaxError(recognizer: Recognizer[?, ?], offendingSymbol: Any, line: Int, charPositionInLine: Int, msg: String, e: RecognitionException): Unit = {
    throw new RuntimeException("line "+line+":"+charPositionInLine+">"+msg, e)
  }

  override def reportAmbiguity(recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int, exact: Boolean, ambigAlts: util.BitSet, configs: ATNConfigSet): Unit = {
    throw new RuntimeException("index "+startIndex+":"+stopIndex)
  }

  override def reportAttemptingFullContext(recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int, conflictingAlts: util.BitSet, configs: ATNConfigSet): Unit = {
    throw new RuntimeException("index "+startIndex+":"+stopIndex)
  }

  override def reportContextSensitivity(recognizer: Parser, dfa: DFA, startIndex: Int, stopIndex: Int, prediction: Int, configs: ATNConfigSet): Unit = {
    throw new RuntimeException("index "+startIndex+":"+stopIndex+":"+prediction)
  }

}

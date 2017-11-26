package app

import interpreter.InterpreterComponent
import lexer.LexerComponent
import parser.ParserComponent

/**
  * Created by egor on 26.11.17.
  */
trait ComponentRegistry extends LexerComponent with ParserComponent with InterpreterComponent {
  def getLexer = new Lexer
  def getParser = new Parser
  def getInterpreter = new Interpreter
}

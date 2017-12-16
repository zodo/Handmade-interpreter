package app

import parser.symbols.Terminal

import scala.util.control.NonFatal

/**
  * Created by egor on 22.10.17.
  */
object SimpleFacade extends ComponentRegistry {

  def run(program: String, input: String): Either[String, String] = {
    {
      val lexer = getLexer
      val parser = getParser
      val interpreter = getInterpreter

      lexer.lexIT("ВАРКОНСТ ПРОГРАММА " + program)
      var thatAll = false
      while (!thatAll) {
        {
          val lexema = lexer.getNext
          parser.parse(Terminal.getTerminalByLexerValue(lexema.tag), lexema.lexeme)
          if (Terminal.getTerminalByLexerValue(lexema.tag) == Terminal.TheEnd) {
            thatAll = true
          }
        }
      }
      Right(interpreter.getResult(parser.getTokenString, input.split(" ")))
    }
  }
}

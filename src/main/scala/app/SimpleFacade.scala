package app

import parser.{Adapter, S}

import scala.util.control.NonFatal

/**
  * Created by egor on 22.10.17.
  */
object SimpleFacade extends ComponentRegistry {

  def run(program: String, input: String): Either[String, String] = {
    try {
      val lexer = getLexer
      val parser = getParser
      val interpreter = getInterpreter

      lexer.lexIT("ВАРКОНСТ ПРОГРАММА " + program)
      var thatAll = false
      while (!thatAll) {
        {
          val lexema = lexer.getNext
          parser.parse(Adapter.Adapt(lexema.tag), lexema.lexeme)
          if (Adapter.Adapt(lexema.tag) == S.THEEND) {
            thatAll = true
          }
        }
      }
      Right(interpreter.getResult(parser.getTokenString, input.split(" ")))
    } catch {
      case NonFatal(e) => Left(e.getMessage)
    }
  }
}

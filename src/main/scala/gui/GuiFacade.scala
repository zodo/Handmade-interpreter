package gui

import interpreter.Interpreter
import lexer.{Lexer, Word}
import parser.{Adapter, Parser, S}

/**
  * Created by egor on 28.10.17.
  */
object GuiFacade {
  private var RPN: String = null
  private var currentLine: Int = 0
  private var currentSymbol: Int = 0
  private var ErrorText: String = null
  private var InterpResult: String = null

  def parseText(text: String, input: String) {
    RPN = null
    ErrorText = null
    currentLine = 0
    val strInput = input.split(" ")
    try {
      val lexer: Lexer = new Lexer("ВАРКОНСТ ПРОГРАММА " + text)
      lexer.lexIT
      val parser: Parser = new Parser
      System.out.println("**************")
      var thatAll = false
      while (!thatAll) {
        {
          val lexema: Word = lexer.getNext
          parser.parse(Adapter.Adapt(lexema.tag), lexema.lexeme)
          currentLine = lexer.getLine
          currentSymbol = lexer.getLine
          System.out.println("lexema: " + S.getSymbolName(Adapter.Adapt(lexema.tag)) + " val: " + lexema.lexeme)
          if (Adapter.Adapt(lexema.tag) == S.THEEND) {
            thatAll = true
          }
        }
      }
      RPN = parser.getRPNString
      val interpretator: Interpreter = new Interpreter(parser)
      interpretator.forInput(strInput)
      InterpResult = interpretator.getResult
    }
    catch {
      case e: Exception =>
//        e.printStackTrace()
        System.out.println(e.toString)
        ErrorText = e.getMessage
    }
  }

  def getErrors: String = {
    return ErrorText
  }

  def getInterpResult: String = {
    return InterpResult
  }

  def getRPN: String = {
    return RPN
  }

  def getLineWithError: Int = {
    if (ErrorText == null) {
      return -1
    }
    return currentLine + 1
  }
}

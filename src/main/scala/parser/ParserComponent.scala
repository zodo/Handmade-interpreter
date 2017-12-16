package parser

import interpreter.TokenJAV
import parser.symbols.{Symbol, Terminal, NonTerminal, Special}
import Terminal._
import NonTerminal._
import Special._

import scala.collection.mutable

trait ParserComponent {

  def getParser: Parser

  /**
    * Created by noname on 23.11.2014.
    */
  object Parser {
    def isNumeric(str: String) = str.matches("-?\\d+")

    def reportInvalidState = throw new Exception("Некорректное состояние парсера")
  }

  class Parser {
    private var shop = new mutable.Stack[Symbol]
    private var workIsDone: Boolean = false
    private var RPN: Parser#RPNhandler = new RPNhandler
    shop.push(TheEnd)
    shop.push(Program)

    def getRPNString: String = {
      RPN.getStr
    }

    def getTokenString: Seq[TokenJAV] = {
      RPN.tokenString
    }

    def getVARMap: Map[String, Int] = {
      RPN.varMap.toMap
    }

    def getCONSTMap: Map[String, Int] = {
      RPN.constMap.toMap
    }

    def getARRAYMap: Map[String, Seq[Int]] = {
      RPN.masMap.toMap
    }

    def parse(term: Terminal, value: String): Boolean = {
      term match {
        case Number if !Parser.isNumeric(value) => throw new Exception("Не правильное значение числа")
        case Id if value == "" || Character.isDigit(value.charAt(0)) => throw new Exception("Неправильное значение ID")
        case _ => // ignore
      }

      RPN.pushValue(value)
      var shouldBreak = false

      while (!shouldBreak) {
        shop.pop match {
          case TheEnd if term == TheEnd =>
            RPN.goOut()
            workIsDone = true
            shouldBreak = true
          case P => RPN.goOut()
          case s: NonTerminal =>
            val zamena = s.getMove(term).reverse
            if (zamena.isEmpty) throw new Exception("Hет перехода в \"" + term.name + "\" из \"" + s.name + "\"")

            val RPNzamena = s.getRpnMove(term).reverse

            shop.pushAll(zamena.filter(_ != Lam))
            RPN.goOut()

            if (!zamena.contains(Lam)) RPN.pushAll(RPNzamena)
          case s: Terminal =>
            if (term == s) {
              RPN.goOut()
              shouldBreak = true
            }
            else {
              throw new Exception("Hа входе \"" + term.name + "\",ожидался \"" + s.name + "\". ")
            }
        }
      }
      workIsDone
    }

    class RPNhandler() {

      var count: Int = 0
      var compOperator: String = ""
      var shop = new mutable.Stack[Symbol]
      var LBLshop = new mutable.Stack[Int]
      var TokenValues = new mutable.Stack[String]
      var string = new mutable.ArrayBuffer[String]
      var tokenString = new mutable.ArrayBuffer[TokenJAV]
      var varMap = new mutable.HashMap[String, Int]
      var constMap = new mutable.HashMap[String, Int]
      var masMap = new mutable.HashMap[String, mutable.Seq[Int]]

      shop.push(N)
      shop.push(N)

      private def getStrFromS(symbol: Symbol): String = symbol match {
        case Number =>
          TKSadd(TokenJAV.num(TokenValues.top.toInt))
          TokenValues.pop
        case Plus =>
          TKSadd(TokenJAV.plus)
          "+"
        case Minus =>
          TKSadd(TokenJAV.minus)
          "-"
        case Mult =>
          TKSadd(TokenJAV.mult)
          "*"
        case Div =>
          TKSadd(TokenJAV.div)
          "/"
        case Id =>
          TKSadd(TokenJAV.id(TokenValues.top))
          TokenValues.pop
        case Ravno =>
          TKSadd(TokenJAV.assign)
          ":="
        case Dot =>
          TKSadd(TokenJAV.arr)
          "<i>"
        case Mle =>
          compOperator match {
            case ">" =>
              TKSadd(TokenJAV.more)
              compOperator
            case "<" =>
              TKSadd(TokenJAV.less)
              compOperator
            case _ =>
              TKSadd(TokenJAV.isEq)
              "=="
          }
        case P =>
          ""
        case Vivod =>
          TKSadd(TokenJAV.out)
          "<OUT>"
        case Vvod =>
          TKSadd(TokenJAV.in)
          "<IN>"
        case _: Special => ""
        case s =>
          toString
      }

      private def addToRPN(s: String) {
        if (s != "") {
          string.append(s)
          count += 1
        }
      }

      private def TKSadd(token: TokenJAV) {
        tokenString.append(token)
      }

      def goOut() = shop.pop match {
        case N =>
        case P1 =>
          LBLshop.push(count)
          addToRPN("pystP1")
          addToRPN("<jf>")
          TKSadd(TokenJAV.incompleteLbl)
          TKSadd(TokenJAV.jumpFalse)

        case P2 =>
          val m: String = "<m" + (count + 2) + ">"
          tokenString.update(LBLshop.top, TokenJAV.lbl(count + 2))
          string.update(LBLshop.pop, m)
          LBLshop.push(count)
          addToRPN("pystP2")
          addToRPN("<j>")
          TKSadd(TokenJAV.incompleteLbl)
          TKSadd(TokenJAV.jump)

        case P3 =>
          val m: String = "<m" + count + ">"
          string.update(LBLshop.top, m)
          tokenString.update(LBLshop.pop, TokenJAV.lbl(count))

        case P4 => LBLshop.push(count)

        case P5 =>
          val m: String = "<m" + (count + 2) + ">"
          string.update(LBLshop.top, m)
          tokenString.update(LBLshop.pop, TokenJAV.lbl(count + 2))
          addToRPN("<m" + LBLshop.top + ">")
          addToRPN("<j>")
          TKSadd(TokenJAV.lbl(LBLshop.pop))
          TKSadd(TokenJAV.jump)

        case Pvar =>
          varMap.put(if (TokenValues.isEmpty) "MissedID&N"
          else TokenValues.pop, 0)

        case Pconst =>
          constMap.put(if (TokenValues.isEmpty) "MissedID&N"
          else TokenValues.pop, 0)

        case Pmas => masMap.put(if (TokenValues.isEmpty) "MissedID&N" else TokenValues.pop, mutable.Seq())
        case More => compOperator = ">"
        case Less => compOperator = "<"
        case Comp => compOperator = "=="
        case s => addToRPN(getStrFromS(s))
      }

      def push(symbol: Symbol) = shop.push(symbol)

      def pushAll(symbols: Seq[Symbol]) = shop.pushAll(symbols)

      def pushValue(s: String) = TokenValues.push(s)

      def getStr: String = {
        if (workIsDone) while (shop.nonEmpty) {
          addToRPN(getStrFromS(shop.pop))
        }
        string = string.filter(_ != "")
        string.mkString(" ")
      }
    }
  }
}
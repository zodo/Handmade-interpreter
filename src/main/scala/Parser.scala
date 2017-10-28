import scala.collection.mutable

/**
  * Created by noname on 23.11.2014.
  */
object Parser {
  def isNumeric(str: String): Boolean = {
    try {
      val i: Int = str.toInt
    }
    catch {
      case _: NumberFormatException => {
        return false
      }
    }
    true
  }
}

class Parser() {
  private var shop = new mutable.Stack[Int]
  private var workIsDone: Boolean = false
  private var RPN: Parser#RPNhandler = new RPNhandler
  shop.push(S.THEEND)
  shop.push(S.PROGRAM)

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

  def parse(term: Int, value: String): Boolean = {
    //        if (workIsDone) return true;
    if (term == S.NUMBER && !Parser.isNumeric(value)) throw new Exception("Не правильное значение числа")
    if (term == S.ID && (value == "" || Character.isDigit(value.charAt(0)))) throw new Exception("Не правильное значение ID")
    RPN.pushValue(value)
    var shouldBreak = false
    while (!shouldBreak) {
      {
        if ((shop.top == S.THEEND) && (term == S.THEEND)) {
          shop.pop
          RPN.goOut()
          workIsDone = true
          shouldBreak = true
        } else if (shop.top < 100) //Нетерминал
        {
          var zamena = table.getMove(shop.top, term)
          if (zamena == null) throw new Exception("Hет перехода в \"" + S.getSymbolName(term) + "\" из \"" + S.getSymbolName(shop.top) + "\"")
          var RPNzamena = table.getRPNmove(shop.top, term)
          zamena = zamena.reverse
          shop.pop
          var lambda: Boolean = false
          for (i <- zamena) if (i != S.LAM) {
            shop.push(i)
          }
          else {
            lambda = true
          }
          RPNzamena = RPNzamena.reverse
          RPN.goOut()
          for (i <- RPNzamena) if (!lambda) RPN.push(i)
        }
        else //Терминал
        {
          if (term == shop.top) {
            shop.pop
            RPN.goOut()
            shouldBreak = true
          }
          else {
            throw new Exception("Hа входе \"" + S.getSymbolName(term) + "\",ожидался \"" + S.getSymbolName(shop.top) + "\". ")
          }
        }
      }
    }
    workIsDone
  }

  class RPNhandler() {

    var count: Int = 0
    var compOperator: String = ""
    var shop = new mutable.Stack[Int]
    var LBLshop = new mutable.Stack[Int]
    var TokenValues = new mutable.Stack[String]
    var string = new mutable.ArrayBuffer[String]
    var tokenString = new mutable.ArrayBuffer[TokenJAV]
    var varMap = new mutable.HashMap[String, Int]
    var constMap = new mutable.HashMap[String, Int]
    var masMap = new mutable.HashMap[String, mutable.Seq[Int]]

    shop.push(S.N)
    shop.push(S.N)

    private def getStrFromInt(symbol: Int): String = {
      if (symbol >= 200) return ""
      symbol match {
        case S.NUMBER =>
          TKSadd(new TKBuild().T(TokenJAV.Op.num).V(TokenValues.top).create)
          TokenValues.pop
        case S.PLUS =>
          TKSadd(new TKBuild().T(TokenJAV.Op.plus).create)
          "+"
        case S.MINUS =>
          TKSadd(new TKBuild().T(TokenJAV.Op.minus).create)
          "-"
        case S.MULT =>
          TKSadd(new TKBuild().T(TokenJAV.Op.mult).create)
          "*"
        case S.DIV =>
          TKSadd(new TKBuild().T(TokenJAV.Op.div).create)
          "/"
        case S.ID =>
          TKSadd(new TKBuild().T(TokenJAV.Op.id).V(TokenValues.top).create)
          TokenValues.pop
        case S.EQ =>
          TKSadd(new TKBuild().T(TokenJAV.Op.ravno).create)
          ":="
        case S.DOT =>
          TKSadd(new TKBuild().T(TokenJAV.Op.arr).create)
          "<i>"
        case S.MLE =>
          if (compOperator == ">") {
            TKSadd(new TKBuild().T(TokenJAV.Op.more).create)
            ">"
          }
          else if (compOperator == "<") {
            TKSadd(new TKBuild().T(TokenJAV.Op.less).create)
            "<"
          }
          else {
            TKSadd(new TKBuild().T(TokenJAV.Op.eq).create)
            "=="
          }
        case S.P =>
          ""
        case S.VIVOD =>
          TKSadd(new TKBuild().T(TokenJAV.Op.out).create)
          "<OUT>"
        case S.VVOD =>
          TKSadd(new TKBuild().T(TokenJAV.Op.in).create)
          "<IN>"
        case s =>
          s.toString
      }
    }

    private def P1() {
      LBLshop.push(count)
      addToRPN("pystP1")
      addToRPN("<jf>")
      TKSadd(new TKBuild().T(TokenJAV.Op.lbl).create)
      TKSadd(new TKBuild().T(TokenJAV.Op.jumpFalse).create)
    }

    private def P2() {
      val m: String = "<m" + (count + 2) + ">"
      tokenString(LBLshop.top).`val` = String.valueOf(count + 2)
      string.update(LBLshop.pop, m)
      LBLshop.push(count)
      addToRPN("pystP2")
      addToRPN("<j>")
      TKSadd(new TKBuild().T(TokenJAV.Op.lbl).create)
      TKSadd(new TKBuild().T(TokenJAV.Op.jump).create)
    }

    private def P3() {
      val m: String = "<m" + (count) + ">"
      string.update(LBLshop.top, m)
      tokenString(LBLshop.pop).`val` = String.valueOf(count)
    }

    private def P4() {
      LBLshop.push(count)
    }

    private def P5() {
      val m: String = "<m" + (count + 2) + ">"
      string.update(LBLshop.top, m)
      tokenString(LBLshop.pop).`val` = String.valueOf(count + 2)
      addToRPN("<m" + LBLshop.top + ">")
      addToRPN("<j>")
      TKSadd(new TKBuild().T(TokenJAV.Op.lbl).V(String.valueOf(LBLshop.pop)).create)
      TKSadd(new TKBuild().T(TokenJAV.Op.jump).create)
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

    def goOut() {
      val symbol: Int = shop.pop
      if (symbol != S.N) {
        symbol match {
          case S.P1 =>
            P1()
          case S.P2 =>
            P2()
          case S.P3 =>
            P3()
          case S.P4 =>
            P4()
          case S.P5 =>
            P5()
          case S.Pvar =>
            varMap.put(if (TokenValues.isEmpty) "MissedID&N"
            else TokenValues.pop, 0)
          case S.Pconst =>
            constMap.put(if (TokenValues.isEmpty) "MissedID&N"
            else TokenValues.pop, 0)
          case S.Pmas =>
            masMap.put(if (TokenValues.isEmpty) "MissedID&N" else TokenValues.pop, mutable.Seq())
          case S.MORE =>
            compOperator = ">"
          case S.LESS =>
            compOperator = "<"
          case S.COMP =>
            compOperator = "=="
          case _ =>
            addToRPN(getStrFromInt(symbol))
        }
      }
    }

    def push(symbol: Int) {
      shop.push(symbol)
    }

    def pushValue(s: String) {
      TokenValues.push(s)
    }

    def getStr: String = {
      if (workIsDone) while (shop.nonEmpty) {
        addToRPN(getStrFromInt(shop.pop))
      }
      string = string.filter(_ != "")
      string.mkString(" ")
    }
  }

}
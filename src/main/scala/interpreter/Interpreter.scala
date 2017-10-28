package interpreter

import java.util.Date

import parser.{Parser, TKBuild}

import scala.collection.mutable

/**
  * Created by noname on 07.12.2014.
  */
class Interpreter(val p: Parser) {
  private var varMap = mutable.HashMap[String, Int]()
  private var tokens = p.getTokenString
  private var mainStack = new mutable.Stack[TokenJAV]
  private var caret: Int = 0
  private var inputs = new mutable.ArrayBuffer[String]()
  private var inputPointer = 0

  def forInput(inputs: Seq[String]) {
    this.inputs = mutable.ArrayBuffer(inputs: _*)
  }

  @throws[Exception]
  def getResult: String = {
    val result = new mutable.ArrayBuffer[String]
    val maxTimeForProcess: Long = new Date().getTime + 1000
    while (caret < tokens.size) {
      {
//        if (new Date().getTime > maxTimeForProcess) throw new Exception("Алгоритм работал дольше секунды")
        val currentToken: TokenJAV = tokens(caret)
        currentToken.`type` match {
          case TokenJAV.Op.num => {
            mainStack.push(currentToken)
            caret += 1
          }
          case TokenJAV.Op.id => {
            mainStack.push(currentToken)
            caret += 1
          }
          case TokenJAV.Op.plus => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            val sum: Int = b + a
            mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(sum.toString).create)
            caret += 1
          }
          case TokenJAV.Op.minus => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            val raznost: Int = b - a
            mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(raznost.toString).create)
            caret += 1
          }
          case TokenJAV.Op.jump => {
            caret = mainStack.pop.`val`.toInt
          }
          case TokenJAV.Op.jumpFalse => {
            val newCaret: Int = mainStack.pop.`val`.toInt
            val condition: Int = mainStack.pop.`val`.toInt
            if (condition == 0) caret = newCaret
            else if (condition == 1) {
              caret += 1; caret - 1
            }
            else {
              throw new Exception("Hе правильное условие")
            }
          }
          case TokenJAV.Op.lbl => {
            mainStack.push(currentToken)
            caret += 1
          }
          case TokenJAV.Op.in => {
            val idName: String = mainStack.pop.`val`
            try {
              varMap.put(idName, inputs(inputPointer).toInt)
              inputPointer += 1
            }
            catch {
              case e: Exception => {
                throw new Exception("Ожидается ввод")
              }
            }
            caret += 1
          }
          case TokenJAV.Op.out => {
            result.append(getNumericNext.toString)
            caret += 1
          }
          case TokenJAV.Op.mult => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            val mult: Int = a * b
            mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(mult.toString).create)
            caret += 1
          }
          case TokenJAV.Op.div => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            if (a == 0) {
              throw new Exception("Деление на ноль")
            }
            val div: Int = b / a
            mainStack.push(new TKBuild().T(TokenJAV.Op.num).V(div.toString).create)
            caret += 1
          }
          case TokenJAV.Op.more => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            if (b > a) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create)
            else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create)
            caret += 1
          }
          case TokenJAV.Op.less => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            if (b < a) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create)
            else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create)
            caret += 1
          }
          case TokenJAV.Op.eq => {
            val a: Int = getNumericNext
            val b: Int = getNumericNext
            if (a == b) mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("1").create)
            else mainStack.push(new TKBuild().T(TokenJAV.Op.num).V("0").create)
            caret += 1
          }
          case TokenJAV.Op.ravno => {
            val left: Int = getNumericNext
            val idName: String = mainStack.pop.`val`
            varMap.put(idName, left)
            caret += 1
          }
          case TokenJAV.Op.arr => {
            val index: Int = getNumericNext
            val idName: String = mainStack.pop.`val`
            varMap.put(s"mID: $idName, index: $index", varMap.getOrElse(s"mID: $idName, index: $index", 0))
            mainStack.push(new TKBuild().T(TokenJAV.Op.id).V(s"mID: $idName, index: $index").create)
            caret += 1
          }
        }
      }
    }
    println(varMap)
    return result.mkString(" ")
  }

  private def getNumericNext: Int = {
    var result: Int = 0
    if (mainStack.top.`type` == TokenJAV.Op.id) {
      try {
        result = varMap(mainStack.pop.`val`)
      }
      catch {
        case e: Exception => {
          throw new Exception("Использование неинициализированной переменной")
        }
      }
    }
    else if (mainStack.top.`type` == TokenJAV.Op.num) {
      result = mainStack.pop.`val`.toInt
    }
    else throw new Exception("Невозможно получить численное значение")
    return result
  }
}
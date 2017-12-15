package interpreter

import java.util.Date

import scala.collection.mutable


trait InterpreterComponent {

  def getInterpreter: Interpreter

  /**
    * Created by noname on 07.12.2014.
    */
  class Interpreter {
    private var varMap = mutable.HashMap[String, Int]()
    private var mainStack = new mutable.Stack[TokenJAV]
    private var caret: Int = 0
    private var inputPointer = 0

    @throws[Exception]
    def getResult(tokens: Seq[TokenJAV], inputs: Seq[String]): String = {
      val result = new mutable.ArrayBuffer[String]
      val maxTimeForProcess: Long = new Date().getTime + 1000
      while (caret < tokens.size) {
        {
          //        if (new Date().getTime > maxTimeForProcess) throw new Exception("Алгоритм работал дольше секунды")
          val currentToken: TokenJAV = tokens(caret)
          currentToken match {
            case TokenJAV.num(_) =>
              mainStack.push(currentToken)
              caret += 1
            case TokenJAV.id(_) =>
              mainStack.push(currentToken)
              caret += 1
            case TokenJAV.plus =>
              val a: Int = getNumericNext
              val b: Int = getNumericNext
              val sum: Int = b + a
              mainStack.push(TokenJAV.num(sum))
              caret += 1
            case TokenJAV.minus =>
              val a: Int = getNumericNext
              val b: Int = getNumericNext
              val raznost: Int = b - a
              mainStack.push(TokenJAV.num(raznost))
              caret += 1
            case TokenJAV.jump =>
              val maybeLbl = mainStack.pop.asInstanceOf[TokenJAV.lbl]
              caret = maybeLbl.value
            case TokenJAV.jumpFalse =>
              val newCaret: Int = mainStack.pop.asInstanceOf[TokenJAV.lbl].value
              val condition: Int = mainStack.pop.asInstanceOf[TokenJAV.num].value
              if (condition == 0) caret = newCaret
              else if (condition == 1) {
                caret += 1
                //                caret - 1
              }
              else {
                throw new Exception("Hе правильное условие")
              }
            case TokenJAV.lbl(_) =>
              mainStack.push(currentToken)
              caret += 1
            case TokenJAV.in =>
              val idName: String = mainStack.pop.asInstanceOf[TokenJAV.id].value
              try {
                varMap.put(idName, inputs(inputPointer).toInt)
                inputPointer += 1
              }
              catch {
                case e: Exception =>
                  throw new Exception("Ожидается ввод")
              }
              caret += 1
            case TokenJAV.out =>
              result.append(getNumericNext.toString)
              caret += 1
            case TokenJAV.mult =>
              val a = getNumericNext
              val b = getNumericNext
              val mult: Int = a * b
              mainStack.push(TokenJAV.num(mult))
              caret += 1
            case TokenJAV.div =>
              val a = getNumericNext
              val b = getNumericNext
              if (a == 0) {
                throw new Exception("Деление на ноль")
              }
              val div: Int = b / a
              mainStack.push(TokenJAV.num(div))
              caret += 1
            case TokenJAV.more =>
              val a = getNumericNext
              val b = getNumericNext
              if (b > a) mainStack.push(TokenJAV.num.trueValue)
              else mainStack.push(TokenJAV.num.falseValue)
              caret += 1
            case TokenJAV.less =>
              val a = getNumericNext
              val b = getNumericNext
              if (b < a) mainStack.push(TokenJAV.num.trueValue)
              else mainStack.push(TokenJAV.num.falseValue)
              caret += 1
            case TokenJAV.eq =>
              val a = getNumericNext
              val b = getNumericNext
              if (a == b) mainStack.push(TokenJAV.num.trueValue)
              else mainStack.push(TokenJAV.num.falseValue)
              caret += 1
            case TokenJAV.ravno =>
              val left = getNumericNext
              val idName = mainStack.pop.asInstanceOf[TokenJAV.id].value
              varMap.put(idName, left)
              caret += 1
            case TokenJAV.arr =>
              val index = getNumericNext
              val idName = mainStack.pop.asInstanceOf[TokenJAV.id].value
              varMap.put(s"mID: $idName, index: $index", varMap.getOrElse(s"mID: $idName, index: $index", 0))
              mainStack.push(TokenJAV.id(s"mID: $idName, index: $index"))
              caret += 1
          }
        }
      }
      println(varMap)
      result.mkString(" ")
    }

    private def getNumericNext = mainStack.pop match {
      case TokenJAV.id(value) => varMap.getOrElse(value, throw new Exception("Использование неинициализированной переменной"))
      case TokenJAV.num(value) => value
      case _ => throw new Exception("Невозможно получить численное значение")
    }
  }

}
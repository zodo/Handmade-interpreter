package interpreter

import interpreter.TokenJAV._

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

    private def moveCaretForward = caret += 1
    private def setCaret(value: Int) = caret = value

    private def getInterpreterVisitor(inputs: Seq[String], result: mutable.ArrayBuffer[String]) = new TokenVisitor {
      private def reportStateError = {
        throw new Exception("Некорректное состояние интерпретатора")
      }

      private def getNumericNext = mainStack.pop match {
        case TokenJAV.id(value) => varMap.getOrElse(value, throw new Exception("Использование неинициализированной переменной"))
        case TokenJAV.num(value) => value
        case _ => reportStateError
      }

      private def binaryOp(func: (Int, Int) => Int) = {
        val a = getNumericNext
        val b = getNumericNext
        val result = func(b, a)
        mainStack.push(TokenJAV.num(result))
        moveCaretForward
      }

      private def conditionOp(func: (Int, Int) => Boolean) = binaryOp((x, y) => if (func(x, y)) num.trueValue.value else num.falseValue.value)

      private def pushAndForward(token: TokenJAV) = {
        mainStack.push(token)
        moveCaretForward
      }

      def visit(token: more.type) = conditionOp(_ > _)

      def visit(token: less.type) = conditionOp(_ < _)

      def visit(token: isEq.type) = conditionOp(_ == _)

      def visit(token: mult.type) = binaryOp(_ * _)

      def visit(token: plus.type) = binaryOp(_ + _)

      def visit(token: minus.type) = binaryOp(_ - _)

      def visit(token: div.type) = binaryOp((x, y) => if (y == 0) throw new Exception("Деление на ноль") else x / y)

      def visit(token: lbl) = pushAndForward(token)

      def visit(token: num) = pushAndForward(token)

      def visit(token: id) = pushAndForward(token)

      def visit(token: in.type) = {
        if (inputPointer >= inputs.length) throw new Exception("Ожидается ввод")

        mainStack.pop match {
          case id(value) =>
            varMap.put(value, inputs(inputPointer).toInt)
            inputPointer += 1
          case _ => reportStateError
        }

        moveCaretForward
      }

      def visit(token: out.type) = {
        result.append(getNumericNext.toString)
        moveCaretForward
      }

      def visit(token: assign.type) = {
        val left = getNumericNext
        mainStack.pop match {
          case id(value) => varMap.put(value, left)
          case _ => reportStateError
        }

        moveCaretForward
      }

      def visit(token: jump.type) = mainStack.pop match {
        case TokenJAV.lbl(value) => setCaret(value)
        case _ => reportStateError
      }

      def visit(token: jumpFalse.type) = {
        val newCaret = mainStack.pop
        val condition = mainStack.pop

        (newCaret, condition) match {
          case (lbl(value), num.falseValue) => setCaret(value)
          case (lbl(_), num.trueValue) => moveCaretForward
          case _ => reportStateError
        }
      }

      def visit(token: arr.type) = {
        val index = getNumericNext

        mainStack.pop match {
          case id(value) =>
            varMap.put(s"mID: $value, index: $index", varMap.getOrElse(s"mID: $value, index: $index", 0))
            mainStack.push(TokenJAV.id(s"mID: $value, index: $index"))
        }
        moveCaretForward
      }
    }

    def getResult(tokens: Seq[TokenJAV], inputs: Seq[String]): String = {
      val result = new mutable.ArrayBuffer[String]
      val visitor = getInterpreterVisitor(inputs, result)

      while (caret < tokens.size) {
        val currentToken = tokens(caret)
        currentToken.accept(visitor)
      }

      result.mkString(" ")
    }
  }
}
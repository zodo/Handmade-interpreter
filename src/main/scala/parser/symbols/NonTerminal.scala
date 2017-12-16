package parser.symbols

/**
  * Created by egor on 16.12.17.
  */
trait NonTerminal extends Symbol {
  def getMove(input: Terminal): Seq[Symbol]
  def getRpnMove(input: Terminal): Seq[Symbol]
}

object NonTerminal {

  import Terminal._
  import Special._

  case object Program extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Varconst => Seq(Varconst, BlPerem, Programa, Code)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Varconst => Seq(N, N, N, N)
      case _ => Nil
    }
  }

  case object BlPerem extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Var => Seq(Var, Id, Dotcom, BlPerem)
      case Const => Seq(Const, Id, Dotcom, BlPerem)
      case Massiv => Seq(Massiv, Id, Dotcom, BlPerem)
      case _ => Seq(Lam)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Var => Seq(N, N, Pvar, N)
      case Const => Seq(N, N, Pconst, N)
      case Massiv => Seq(N, N, Pmas, N)
      case _ => Seq(N)
    }
}

  case object Code extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Esli => Seq(Esli, If, Block, Else, Code)
      case Poka => Seq(Poka, If, Block, Code)
      case Vivod => Seq(Vivod, Operand, Dotcom, Code)
      case Vvod => Seq(Vvod, Variable, Dotcom, Code)
      case Lfscob => Seq(Lfscob, Code, Rfscob, Code)
      case Id => Seq(Id, Array, Ravno, Vyraz, Dotcom, Code)
      case _ => Seq(Lam)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Esli => Seq(N, N, P1, N, N)
      case Poka => Seq(P4, N, P1, P5)
      case Vivod => Seq(N, N, Vivod, N)
      case Vvod => Seq(N, N, Vvod, N)
      case Lfscob => Seq(N, N, N, N)
      case Id => Seq(Id, N, N, N, Ravno, N)
      case _ => Seq(N)
    }
  }

  case object Block extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Esli => Seq(Esli, If, Block, Else)
      case Poka => Seq(Poka, If, Block)
      case Vivod => Seq(Vivod, Operand, Dotcom)
      case Vvod => Seq(Vvod, Variable, Dotcom)
      case Lfscob => Seq(Lfscob, Code, Rfscob)
      case Id => Seq(Id, Array, Ravno, Vyraz, Dotcom)
      case _ => Nil
    }

    override def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Esli => Seq(N, N, P1, N)
      case Poka => Seq(P4, P1, P5)
      case Vivod => Seq(N, N, Vivod)
      case Vvod => Seq(N, N, Vvod)
      case Lfscob => Seq(N, N, N)
      case Id => Seq(Id, N, N, N, Ravno)
      case _ => Nil
    }
}

  case object Else extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Inache => Seq(Inache, Block, P)
      case _ => Seq(P)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Inache => Seq(P2, N, P3)
      case _ => Seq(P3)
    }
}

  case object If extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(Lskob, Vyraz, Operator, Vyraz, Rskob, P)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(N, N, N, N, Mle, N)
      case _ => Nil
    }
  }

  case object Operator extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Ravno => Seq(Ravno)
      case Less => Seq(Less)
      case More => Seq(More)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Ravno => Seq(Comp)
      case Less => Seq(Less)
      case More => Seq(More)
      case _ => Nil
    }
}

  case object Vyraz extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(Lskob, Vyraz, Rskob, Term_2, Vyraz_2)
      case Id => Seq(Id, Array, Term_2, Vyraz_2)
      case Number => Seq(Number, Term_2, Vyraz_2)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(N, N, N, N, N)
      case Id => Seq(Id, N, N, N)
      case Number => Seq(Number, N, N)
      case _ => Nil
    }
}

  case object Vyraz_2 extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Plus => Seq(Plus, Term, Vyraz_2)
      case Minus => Seq(Minus, Term, Vyraz_2)
      case _ => Seq(Lam)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Plus => Seq(N, N, Plus)
      case Minus => Seq(N, N, Minus)
      case _ => Seq(N)
    }
}

  case object Term extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(Lskob, Vyraz, Rskob, Term_2)
      case Id => Seq(Id, Array, Term_2)
      case Number => Seq(Number, Term_2)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(N, N, N, N)
      case Id => Seq(Id, N, N)
      case Number => Seq(Number, N)
      case _ => Nil
    }
}

  case object Term_2 extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Mult => Seq(Mult, Factor, Term_2)
      case Div => Seq(Div, Factor, Term_2)
      case _ => Seq(Lam)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Mult => Seq(N, N, Mult)
      case Div => Seq(N, N, Div)
      case _ => Seq(N)
    }
}

  case object Factor extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(Lskob, Vyraz, Rskob)
      case Id => Seq(Id, Array)
      case Number => Seq(Number)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Lskob => Seq(N, N, N)
      case Id => Seq(Id, N)
      case Number => Seq(Number)
      case _ => Nil
    }
}

  case object Operand extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Id => Seq(Id, Array)
      case Number => Seq(Number)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Id => Seq(Id, N)
      case Number => Seq(Number)
      case _ => Nil
    }
}

  case object Variable extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Id => Seq(Id, Array)
      case _ => Nil
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Id => Seq(Id, N)
      case _ => Nil
    }

  }

  case object Array extends NonTerminal {
    def getMove(input: Terminal): Seq[Symbol] = input match {
      case Dot => Seq(Dot, Operand, P)
      case _ => Seq(P, P, P)
    }

    def getRpnMove(input: Terminal): Seq[Symbol] = input match {
      case Dot => Seq(N, N, Dot)
      case _ => Seq(N, N, N)
    }
  }

  case object P extends NonTerminal {
    def getMove(inputTerminal: Terminal): Seq[Symbol] = Nil

    def getRpnMove(inputTerminal: Terminal): Seq[Symbol] = Nil
  }
}
package parser.symbols

/**
  * Created by egor on 16.12.17.
  */
trait Terminal extends Symbol {
  val lexerValue: Int
}

object Terminal {
  val Terminals = Seq(Varconst, Programa, Var, Const, Massiv, Esli, Inache, Poka, Dotcom, Vivod, Vvod, Ravno, Less, More,
    Plus, Minus, Mult, Div, Lskob, Rskob, Lfscob, Rfscob, Dot, Id, Number, TheEnd)

  def getTerminalByLexerValue(value: Int) = Terminals.find(_.lexerValue == value).get

  case object Varconst extends Terminal {
    val lexerValue: Int = 256
  }

  case object Programa extends Terminal {
    val lexerValue: Int = 257
  }

  case object Var extends Terminal {
    val lexerValue: Int = 258
  }

  case object Const extends Terminal {
    val lexerValue: Int = 259
  }

  case object Massiv extends Terminal {
    val lexerValue: Int = 260
  }

  case object Esli extends Terminal {
    val lexerValue: Int = 261
  }

  case object Inache extends Terminal {
    val lexerValue: Int = 262
  }

  case object Poka extends Terminal {
    val lexerValue: Int = 263
  }

  case object Dotcom extends Terminal {
    val lexerValue: Int = ';'
  }

  case object Vivod extends Terminal {
    val lexerValue: Int = 264
  }

  case object Vvod extends Terminal {
    val lexerValue: Int = 265
  }

  case object Ravno extends Terminal {
    val lexerValue: Int = '='
  }

  case object Less extends Terminal {
    val lexerValue: Int = '<'
  }

  case object More extends Terminal {
    val lexerValue: Int = '>'
  }

  case object Plus extends Terminal {
    val lexerValue: Int = '+'
  }

  case object Minus extends Terminal {
    val lexerValue: Int = '-'
  }

  case object Mult extends Terminal {
    val lexerValue: Int = '*'
  }

  case object Div extends Terminal {
    val lexerValue: Int = '/'
  }

  case object Lskob extends Terminal {
    val lexerValue: Int = '('
  }

  case object Rskob extends Terminal {
    val lexerValue: Int = ')'
  }

  case object Lfscob extends Terminal {
    val lexerValue: Int = '{'
  }

  case object Rfscob extends Terminal {
    val lexerValue: Int = '}'
  }

  case object Dot extends Terminal {
    val lexerValue: Int = '.'
  }

  case object Id extends Terminal {
    val lexerValue: Int = 286
  }

  case object Number extends Terminal {
    val lexerValue: Int = 287
  }

  case object TheEnd extends Terminal {
    val lexerValue: Int = 289
  }
}
package parser.symbols

/**
  * Created by egor on 16.12.17.
  */
trait Special extends Symbol

object Special {
  case object Lam extends Special

  case object Comp extends Special

  case object Mle extends Special

  case object N extends Special

  case object P1 extends Special

  case object P2 extends Special

  case object P3 extends Special

  case object P4 extends Special

  case object P5 extends Special

  case object Pvar extends Special

  case object Pconst extends Special

  case object Pmas extends Special
}
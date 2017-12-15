package interpreter

trait TokenJAV

/**
  * Created by noname on 29.11.2014.
  */
object TokenJAV {

  case class num(value: Int) extends TokenJAV
  object num {
    val trueValue = num(1)
    val falseValue = num(0)
  }

  case class id(value: String) extends TokenJAV
  case object more extends TokenJAV
  case object less extends TokenJAV
  case object eq extends TokenJAV
  case object ravno extends TokenJAV
  case object jump extends TokenJAV
  case object jumpFalse extends TokenJAV
  case class lbl(value: Int) extends TokenJAV
  case object incompleteLbl extends TokenJAV
  case object in extends TokenJAV
  case object out extends TokenJAV
  case object mult extends TokenJAV
  case object div extends TokenJAV
  case object plus extends TokenJAV
  case object minus extends TokenJAV
  case object arr extends TokenJAV
}

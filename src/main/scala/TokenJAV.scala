/**
  * Created by noname on 29.11.2014.
  */
object TokenJAV {

  object Op extends Enumeration {
    val num, id, more, less, eq, ravno, jump, jumpFalse, lbl, in, out, mult, div, plus, minus, arr = Value
  }

}

case class TokenJAV(var `type`: TokenJAV.Op.Value, var `val`: String) {
}
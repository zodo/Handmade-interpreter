/**
  * Created by egor on 28.10.17.
  */
object Preprocessor {
  def preprocess(program: String) = {
    def aux(replacements: Seq[(String, String)], result: String): String = replacements match {
      case Seq() => result
      case (from, to) +: tail => aux(tail, result.replace(from, to))
    }

    aux(replacements, program.toLowerCase)
  }

  private val replacements = Seq(
    "variables" -> "ВАРКОНСТ",
    "program" -> "ПРОГРАММА",
    "var" -> "ПЕРЕМЕННАЯ",
    "print" -> "ВЫВОД",
    "read" -> "ВВОД",
    "if" -> "ЕСЛИ",
    "else" -> "ИНАЧЕ",
    "val" -> "КОНСТ",
    "while" -> "ПОКА",
    "array" -> "МАССИВ"
  )
}

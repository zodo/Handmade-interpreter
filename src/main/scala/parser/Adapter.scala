package parser

/**
  * Created by noname on 19.12.2014.
  */
object Adapter {
  def Adapt(source: Int): Int = {
    source match {
      case 256 => S.VARCONST // ВАРКОНСТ
      case 257 => S.PROGRAMA // ПРОГРАММА
      case 258 => S.VAR // ПЕРЕМЕННАЯ
      case 259 => S.CONST // КОНСТ
      case 260 => S.MASSIV // МАССИВ
      case 261 => S.ESLI // ЕСЛИ
      case 262 => S.INACHE // ИНАЧЕ
      case 263 => S.POKA // ПОКА
      case 264 => S.VIVOD // ВЫВОД
      case 265 => S.VVOD // ВВОД
      case 286 => S.ID // ID
      case 287 => S.NUMBER // ЧИСЛО
      case 289 => S.THEEND
      case s if s == '('.toInt => S.LSKOB
      case s if s == ')'.toInt => S.RSKOB
      case s if s == '.'.toInt => S.DOT
      case s if s == '<'.toInt => S.LESS
      case s if s == '='.toInt => S.EQ
      case s if s == '>'.toInt => S.MORE
      case s if s == '{'.toInt => S.LFSCOB
      case s if s == '}'.toInt => S.RFSCOB
      case s if s == '+'.toInt => S.PLUS
      case s if s == '-'.toInt => S.MINUS
      case s if s == '*'.toInt => S.MULT
      case s if s == '/'.toInt => S.DIV
      case s if s == ';'.toInt => S.DOTCOM
      case _ => 289
    }
  }
}

//1. (
//        2. )
//        3. .
//        4. <
//5. =
//        6. >
//        7. ID
//        8. {
//        9. }
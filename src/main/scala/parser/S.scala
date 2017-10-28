package parser

import java.util.HashMap

/**
  * Created by noname on 23.11.2014.
  */
object S {
  val PROGRAM: Int = 0
  val BL_PEREM: Int = 1
  val CODE: Int = 2
  val BLOK: Int = 3
  val ELSE: Int = 4
  val IF: Int = 5
  val OPERATOR: Int = 6
  val VYRAZ: Int = 7
  val VYRAZ_2: Int = 8
  val TERM: Int = 9
  val TERM_2: Int = 10
  val FACTOR: Int = 11
  val OPERAND: Int = 12
  val VARIABLE: Int = 13
  val ARRAY: Int = 14
  val LAM: Int = 15
  val P: Int = 16
  val VARCONST: Int = 100
  val PROGRAMA: Int = 101
  val VAR: Int = 102
  val CONST: Int = 103
  val MASSIV: Int = 104
  val ESLI: Int = 105
  val INACHE: Int = 106
  val POKA: Int = 107
  val DOTCOM: Int = 108
  val VIVOD: Int = 109
  val VVOD: Int = 110
  val EQ: Int = 111
  val LESS: Int = 112
  val MORE: Int = 113
  val PLUS: Int = 114
  val MINUS: Int = 115
  val MULT: Int = 116
  val DIV: Int = 117
  val LSKOB: Int = 118
  val RSKOB: Int = 119
  val LFSCOB: Int = 120
  val RFSCOB: Int = 121
  val DOT: Int = 122
  val ID: Int = 123
  val NUMBER: Int = 124
  val THEEND: Int = 125
  val COMP: Int = 126
  val MLE: Int = 127
  val N: Int = 200
  val P1: Int = 201
  val P2: Int = 202
  val P3: Int = 203
  val P4: Int = 204
  val P5: Int = 205
  val Pvar: Int = 206
  val Pconst: Int = 207
  val Pmas: Int = 208
  var map: HashMap[Int, String] = null

  private def fillMap() {
    map = new HashMap[Int, String]
    map.put(0, "PROGRAM")
    map.put(1, "BL_PEREM")
    map.put(2, "CODE")
    map.put(3, "BLOK")
    map.put(4, "ELSE")
    map.put(5, "IF")
    map.put(6, "OPERATOR")
    map.put(7, "VYRAZ")
    map.put(8, "VYRAZ_2")
    map.put(9, "TERM")
    map.put(10, "TERM_2")
    map.put(11, "FACTOR")
    map.put(12, "OPERAND")
    map.put(13, "VARIABLE")
    map.put(14, "ARRAY")
    map.put(15, "LAM")
    map.put(16, "P")
    map.put(100, "варконст")
    map.put(101, "програма")
    map.put(102, "вар")
    map.put(103, "конст")
    map.put(104, "массив")
    map.put(105, "если")
    map.put(106, "иначе")
    map.put(107, "пока")
    map.put(108, "\" ; \"")
    map.put(109, "вывод")
    map.put(110, "ввод")
    map.put(111, "=")
    map.put(112, "<")
    map.put(113, ">")
    map.put(114, "+")
    map.put(115, "-")
    map.put(116, "*")
    map.put(117, "/")
    map.put(118, "(")
    map.put(119, ")")
    map.put(120, "{")
    map.put(121, "}")
    map.put(122, "\" . \"")
    map.put(123, "ID")
    map.put(124, "NUMBER")
    map.put(125, "конец программы")
    map.put(126, "COMP")
    map.put(127, "MLE")
    map.put(200, "N")
    map.put(201, "P1")
    map.put(202, "P2")
    map.put(203, "P3")
    map.put(204, "P4")
    map.put(205, "P5")
    map.put(206, "Pvar")
    map.put(207, "Pconst")
    map.put(208, "Pmas")
  }

  def getSymbolName(symbol: Int): String = {
    if (map == null) {
      fillMap()
    }
    return map.get(symbol)
  }
}
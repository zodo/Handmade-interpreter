package lexer

/**
 * Created by Ognelis on 21.11.14.
 */
object Tag {
  val VARCONST: Int = 256  // ВАРКОНСТ
  val PROGRAMM: Int = 257  // ПРОГРАММА
  val VARIABLE: Int = 258  // ПЕРЕМЕННАЯ
  val CONSTANT: Int = 259  // КОНСТ
  val ARRAY: Int    = 260  // МАССИВ
  val IF: Int       = 261  // ЕСЛИ
  val ELSE: Int     = 262  // ИНАЧЕ
  val WHILE: Int    = 263  // ПОКА
  val OUTPUT: Int   = 264  // ВЫВОД
  val INPUT: Int    = 265  // ВВОД
  val ID:  Int      = 286  // ID
  val Int: Int  = 287  // ЧИСЛО
  val WHITE_SPACE   = 288
  val END_PROGRAMM  = 289
}


/*
1. (
2. )
3. .
4. <
5. =
6. >
7. ID
8. {
9. }
10. ВАРКОНСТ
11. ВВОД
12. ВЫВОД
13. ЕСЛИ
14. ИНАЧЕ
15. КОНСТ
16. МАССИВ
17. ПЕРЕМЕННАЯ
18. ПОКА
19. ПРОГРАММА
20. ЧИСЛО
 */
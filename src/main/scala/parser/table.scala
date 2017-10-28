package parser

/**
  * Created by noname on 23.11.2014.
  */
object table {
  private var storage: Seq[Seq[Seq[Int]]] = null
  private var forRPN: Seq[Seq[Seq[Int]]] = null

  private def fillList() {
    storage = Seq(
                                        Seq(L(S.VARCONST, S.BL_PEREM, S.PROGRAMA, S.CODE), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L()), /*БЛ_ПЕРЕМ */
                                        Seq(L(S.LAM), L(S.LAM), L(S.VAR, S.ID, S.DOTCOM, S.BL_PEREM), L(S.CONST, S.ID, S.DOTCOM, S.BL_PEREM), L(S.MASSIV, S.ID, S.DOTCOM, S.BL_PEREM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM)), /*КОД      */
                                        Seq(L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.ESLI, S.IF, S.BLOK, S.ELSE, S.CODE), L(S.LAM), L(S.POKA, S.IF, S.BLOK, S.CODE), L(S.LAM), L(S.VIVOD, S.OPERAND, S.DOTCOM, S.CODE), L(S.VVOD, S.VARIABLE, S.DOTCOM, S.CODE), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LFSCOB, S.CODE, S.RFSCOB, S.CODE), L(S.LAM), L(S.LAM), L(S.ID, S.ARRAY, S.EQ, S.VYRAZ, S.DOTCOM, S.CODE), L(S.LAM), L(S.LAM)), /*БЛОК     */
                                        Seq(L(), L(), L(), L(), L(), L(S.ESLI, S.IF, S.BLOK, S.ELSE), L(), L(S.POKA, S.IF, S.BLOK), L(), L(S.VIVOD, S.OPERAND, S.DOTCOM), L(S.VVOD, S.VARIABLE, S.DOTCOM), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.LFSCOB, S.CODE, S.RFSCOB), L(), L(), L(S.ID, S.ARRAY, S.EQ, S.VYRAZ, S.DOTCOM), L(), L()), /*ЕЛСЕ  */
                                        Seq(L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.INACHE, S.BLOK, S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P), L(S.P)), /*УСЛОВИЕ*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.LSKOB, S.VYRAZ, S.OPERATOR, S.VYRAZ, S.RSKOB, S.P), L(), L(), L(), L(), L(), L(), L()), /*ОПЕРАТОР*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.EQ), L(S.LESS), L(S.MORE), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L()), /*ВЫРАЖ*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.LSKOB, S.VYRAZ, S.RSKOB, S.TERM_2, S.VYRAZ_2), L(), L(), L(), L(), L(S.ID, S.ARRAY, S.TERM_2, S.VYRAZ_2), L(S.NUMBER, S.TERM_2, S.VYRAZ_2), L()), /*ВЫРАЖ_2 */
                                        Seq(L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.PLUS, S.TERM, S.VYRAZ_2), L(S.MINUS, S.TERM, S.VYRAZ_2), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM)), /*ТЕРМ */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.LSKOB, S.VYRAZ, S.RSKOB, S.TERM_2), L(), L(), L(), L(), L(S.ID, S.ARRAY, S.TERM_2), L(S.NUMBER, S.TERM_2), L()), /*ТЕРМ_2 */
                                        Seq(L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.MULT, S.FACTOR, S.TERM_2), L(S.DIV, S.FACTOR, S.TERM_2), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM), L(S.LAM)), /*ФАКТОР */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.LSKOB, S.VYRAZ, S.RSKOB), L(), L(), L(), L(), L(S.ID, S.ARRAY), L(S.NUMBER), L()), /*ОПЕРАНД */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.ID, S.ARRAY), L(S.NUMBER), L()), /*ПЕРЕМЕНН*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.ID, S.ARRAY), L(), L()), /*МАССИВ */
                                        Seq(L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.DOT, S.OPERAND, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P), L(S.P, S.P, S.P)))
    forRPN = Seq(
                                        Seq(L(S.N, S.N, S.N, S.N), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L()), /*БЛ_ПЕРЕМ */
                                        Seq(L(S.N), L(S.N), L(S.N, S.N, S.Pvar, S.N), L(S.N, S.N, S.Pconst, S.N), L(S.N, S.N, S.Pmas, S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N)), /*КОД      */
                                        Seq(L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N, S.N, S.P1, S.N, S.N), L(S.N), L(S.P4, S.N, S.P1, S.P5), L(S.N), L(S.N, S.N, S.VIVOD, S.N), L(S.N, S.N, S.VVOD, S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N, S.N, S.N, S.N), L(S.N), L(S.N), L(S.ID, S.N, S.N, S.N, S.EQ, S.N), L(S.N), L(S.N)), /*БЛОК     */
                                        Seq(L(), L(), L(), L(), L(), L(S.N, S.N, S.P1, S.N), L(), L(S.P4, S.P1, S.P5), L(), L(S.N, S.N, S.VIVOD), L(S.N, S.N, S.VVOD), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.N, S.N, S.N), L(), L(), L(S.ID, S.N, S.N, S.N, S.EQ), L(), L()), /*ЕЛСЕ  */
                                        Seq(L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P2, S.N, S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3), L(S.P3)), /*УСЛОВИЕ*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.N, S.N, S.N, S.N, S.MLE, S.N), L(), L(), L(), L(), L(), L(), L()), /*ОПЕРАТОР*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.COMP), L(S.LESS), L(S.MORE), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L()), /*ВЫРАЖ*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.N, S.N, S.N, S.N, S.N), L(), L(), L(), L(), L(S.ID, S.N, S.N, S.N), L(S.NUMBER, S.N, S.N), L()), /*ВЫРАЖ_2 */
                                        Seq(L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N, S.N, S.PLUS), L(S.N, S.N, S.MINUS), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N)), /*ТЕРМ */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.N, S.N, S.N, S.N), L(), L(), L(), L(), L(S.ID, S.N, S.N), L(S.NUMBER, S.N), L()), /*ТЕРМ_2 */
                                        Seq(L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N, S.N, S.MULT), L(S.N, S.N, S.DIV), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N), L(S.N)), /*ФАКТОР */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.N, S.N, S.N), L(), L(), L(), L(), L(S.ID, S.N), L(S.NUMBER), L()), /*ОПЕРАНД */
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.ID, S.N), L(S.NUMBER), L()), /*ПЕРЕМЕНН*/
                                        Seq(L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(), L(S.ID, S.N), L(), L()), /*МАССИВ */
                                        Seq(L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.DOT), L(S.N, S.N, S.N), L(S.N, S.N, S.N), L(S.N, S.N, S.N)))
  }

  private def L(syms: Int*): Seq[Int] = {
    if (syms.length != 0) {
      return syms.toSeq
    }
    else return null
  }

  def getMove(neterminal: Int, terminal: Int): Seq[Int] = {
    if (storage == null) {
      fillList()
    }
    if (neterminal == S.P) return Seq()
    val result = storage(neterminal)(terminal - 100)
    if (result == null) {
      return null
    }
    else {
      return storage(neterminal)(terminal - 100)
    }
  }

  def getRPNmove(neterminal: Int, terminal: Int): Seq[Int] = {
    if (forRPN == null) {
      fillList()
    }
    if (neterminal == S.P) return Seq()
    return forRPN(neterminal)(terminal - 100)
  }
}

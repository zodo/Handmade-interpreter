package lexer

/**
 * Created by Ognelis on 18.12.14.
 */
class Lexer(val TEXT: String) {
    var symbol: Char = ' '
    var state: Int = 0
    val tTable = new TransitionTable
    var counterSymbol: Int = 0
    var counterLines: Int = 0
    var iter = tTable.outArray.toIterator

    def getNext: Word = {
      if(iter.hasNext) iter.next else new Word("КОНЕЦ", Tag.END_PROGRAMM)
    }
    def getLine: Int = counterLines
    def getSymbol: Int = counterSymbol

    def lexIT = {
      for (symbol <- TEXT) {
        counterSymbol+=1
        symbol match {
          case _ if (symbol == '\n') => {
            counterLines += 1
            counterSymbol = 0
            tTable.ch = symbol
            state = tTable.newState
            tTable.move(state, ' ')
          }
          case _ if (symbol.isWhitespace) => {
            tTable.ch = symbol
            state = tTable.newState
            tTable.move(state, ' ')
          }
          case _ if (symbol.isDigit) => {
            tTable.ch = symbol
            state = tTable.newState
            tTable.move(state, '1')
          }
          case _ if (symbol.isLetter && symbol.toInt < 255) => {
            tTable.ch = symbol
            state = tTable.newState
            tTable.move(state, 'a')
          }
          case _ => {
            tTable.ch = symbol
            state = tTable.newState
            tTable.move(state, symbol)
          }
        }
      }
      tTable.outArray += new Word("КОНЕЦ", Tag.END_PROGRAMM)
      iter = tTable.outArray.toIterator
    }
}

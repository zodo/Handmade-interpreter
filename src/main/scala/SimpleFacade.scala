import scala.util.control.NonFatal

/**
  * Created by egor on 22.10.17.
  */
object SimpleFacade {

  def run(program: String, input: String): Either[String, String] = {
    try {
      val lexer: Lexer = new Lexer("ВАРКОНСТ ПРОГРАММА " + program)
      lexer.lexIT
      val parser: Parser = new Parser
      var thatAll = false
      while (!thatAll) {
        {
          val lexema: Word = lexer.getNext
          parser.parse(Adapter.Adapt(lexema.tag), lexema.lexeme)
          if (Adapter.Adapt(lexema.tag) == S.THEEND) {
            thatAll = true
          }
        }
      }
      val rpn = parser.getRPNString
      val interpretator: Interpretator = new Interpretator(parser)
      interpretator.forInput(input.split(" "))
      Right(interpretator.getResult)
    } catch {
      case NonFatal(e) => Left(e.getMessage)
    }
  }
}

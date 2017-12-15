package interpreter

trait TokenJAV {
  def accept(visitor: TokenVisitor)
}

/**
  * Created by noname on 29.11.2014.
  */
object TokenJAV {

  case class num(value: Int) extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  object num {
    val trueValue = num(1)
    val falseValue = num(0)
  }

  case class id(value: String) extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object more extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object less extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object isEq extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object assign extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object jump extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object jumpFalse extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case class lbl(value: Int) extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object incompleteLbl extends TokenJAV {
    def accept(visitor: TokenVisitor) = ???
  }

  case object in extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object out extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object mult extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object div extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object plus extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object minus extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }

  case object arr extends TokenJAV {
    def accept(visitor: TokenVisitor) = visitor.visit(this)
  }
}

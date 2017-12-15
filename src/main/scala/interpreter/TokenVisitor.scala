package interpreter

import interpreter.TokenJAV._

/**
  * Created by egor on 15.12.17.
  */
trait TokenVisitor {
  def visit(token: arr.type)

  def visit(token: minus.type)

  def visit(token: plus.type)

  def visit(token: div.type)

  def visit(token: mult.type)

  def visit(token: out.type)

  def visit(token: in.type)

  def visit(token: lbl)

  def visit(token: jumpFalse.type)

  def visit(token: jump.type)

  def visit(token: assign.type)

  def visit(token: isEq.type)

  def visit(token: less.type)

  def visit(token: more.type)

  def visit(token: id)

  def visit(token: num)
}

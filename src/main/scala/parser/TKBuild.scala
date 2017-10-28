package parser

import interpreter.TokenJAV

/**
  * Created by noname on 30.11.2014.
  */
class TKBuild() {
  private var tok: TokenJAV.Op.Value = null
  private var `val`: String = ""

  def create: TokenJAV = {
    TokenJAV(tok, `val`)
  }

  def T(tok: TokenJAV.Op.Value): TKBuild = {
    this.tok = tok
    this
  }

  def V(`val`: String): TKBuild = {
    this.`val` = `val`
    this
  }
}
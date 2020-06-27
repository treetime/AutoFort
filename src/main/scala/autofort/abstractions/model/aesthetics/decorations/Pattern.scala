package autofort.abstractions.model.aesthetics.decorations

trait Pattern

object Pattern {


  case class Checkers(side: Int) extends Pattern
  case class Circles(radius: Int) extends Pattern
  case class Lines(length1: Int, length2: Int, orientation) extends Pattern


}
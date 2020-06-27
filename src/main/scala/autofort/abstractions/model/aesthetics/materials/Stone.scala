package autofort.abstractions.model.aesthetics.materials

trait Stone extends Material

object Stone {

  case class Diorite() extends Stone
  case class Marble() extends Stone
  case class Granite() extends Stone
}

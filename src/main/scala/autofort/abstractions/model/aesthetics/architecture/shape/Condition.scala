package autofort.abstractions.model.aesthetics.architecture.shape

import autofort.abstractions.model.aesthetics.architecture.shape.Condition._

/**
 * Conditions define shape boundaries by providing rules for filtering blocks which aren't in the shape
 * */
case class Condition(function: Int => Int, direction: Direction) {
  def holds(x: Int, y: Int): Boolean = {
    val result = function(x)
    direction match {
      case UP    => y >= result
      case DOWN  => y <= result
      case LEFT  => x <= result
      case RIGHT => x >= result
    }
  }
}
object Condition {
  sealed trait Direction
  case object UP extends Direction
  case object DOWN extends Direction
  case object LEFT extends Direction
  case object RIGHT extends Direction
}

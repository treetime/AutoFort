package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{Condition, DOWN, Direction, LEFT, RIGHT, UP}

case class ShapePointPair(idx: Int, p1: ShapePoint, p2: ShapePoint) {
  def horizontal: Boolean = p1.y == p2.y

  def function(scale: Int): Condition = {
    if (!vertical) {
      val gradient = (p2.y - p1.y) / (p2.x - p1.x)
      val intercept = p1.y - p1.x * gradient
      val function = (x: Int) =>
        Math.round(scale * (gradient * x + intercept)).toInt
      Condition(function, direction)

    } else {
      val function = (x: Int) => Math.round(scale * p1.y).toInt
      Condition(function, direction)
    }
  }

  def vertical: Boolean = p1.x == p2.x

  def direction: Direction = (p1.x, p2.x, p1.y, p2.y) match {
    case (x1, x2, _, _) if x2 > x1 => DOWN
    case (x1, x2, _, _) if x1 > x2 => UP
    case (_, _, y1, y2) if y2 > y1 => LEFT
    case (_, _, y1, y2) if y1 > y2 => RIGHT
    case other =>
      throw new RuntimeException(
        s"cant have the exact same two points: ($p1, $p2)"
      )
  }
}

object ShapePointPair {

  def apply(pair: ((ShapePoint, ShapePoint), Int)): ShapePointPair =
    new ShapePointPair(pair._2, pair._1._1, pair._1._2)

}

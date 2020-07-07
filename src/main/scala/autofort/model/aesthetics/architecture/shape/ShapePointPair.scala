package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.architecture.shape.ShapePointPair.{
  CUTS_LINE,
  Intersection,
  NO_TOUCH,
  ON_LINE
}
import autofort.model.map.GridBlock

case class ShapePointPair(idx: Int, p1: ShapePoint, p2: ShapePoint) {
  def horizontal: Boolean = p1.y == p2.y
  def vertical: Boolean = p1.x == p2.x

  def intersects(block: GridBlock): Intersection = {
    val (yMax, yMin) = if (p1.y > p2.y) (p1.y, p2.y) else (p2.y, p1.y)
    if (block.y < yMax && block.y >= yMin) {
      val (pMax, pMin) = if (p1.x > p2.x) (p1, p2) else (p2, p1)
      if (vertical) {
        if (block.x == Math.round(pMax.x).toInt) ON_LINE
        else if (block.x < Math.floor(pMin.x)) CUTS_LINE
        else NO_TOUCH
      } else if (horizontal) {
        if (block.x >= Math.floor(pMin.x) && block.x < Math.ceil(pMax.x)) ON_LINE
        else if (block.x < Math.floor(pMin.x) || block.x > Math.ceil(pMax.x)) CUTS_LINE
        else NO_TOUCH
      } else {
        val gradient = (pMax.y - pMin.y) / (pMax.x - pMin.x)
        val intercept = pMin.y - pMin.x * gradient
        val x = Math.round((block.y - intercept) / gradient)
        if (x == block.x) {
          ON_LINE
        } else if (block.x < x) {
          CUTS_LINE
        } else {
          NO_TOUCH
        }
      }
    } else {
      NO_TOUCH
    }
  }

  def inBetween(f: ShapePoint => Double, vOther: Double): Boolean = {
    vOther >= Math.min(f(p1), f(p2)) && vOther <= Math.max(f(p1), f(p2))
  }

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

  def scaled(scale: Double): ShapePointPair =
    copy(
      p1 = p1.copy(Math.round(p1.x * scale), Math.round(p1.y * scale)),
      p2 = p2.copy(Math.round(p2.x * scale), Math.round(p2.y * scale))
    )
}

object ShapePointPair {

  def apply(pair: ((ShapePoint, ShapePoint), Int)): ShapePointPair =
    new ShapePointPair(pair._2, pair._1._1, pair._1._2)

  trait Intersection
  case object ON_LINE extends Intersection
  case object CUTS_LINE extends Intersection
  case object NO_TOUCH extends Intersection

}

package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.architecture.shape.ShapePointPair.{
  CUTS_LINE,
  Intersection,
  ON_LINE
}
import autofort.model.map.GridBlock

case class ShapePointPair(idx: Int, p1: ShapePoint, p2: ShapePoint) {

  def intersect(block: GridBlock): Option[Intersection] = {
    if (afterThis(block)) {
      None
    } else {
      val (x, y) = (block.x, block.y)
      if (vertical) {
        if (p2.isAbove(p1)) {
          if (y < p2.y && y >= p1.y) {
            if (p1.x == x) { Option(ON_LINE) } else { Option(CUTS_LINE) }
          } else {
            None
          }
        } else {
          if (y <= p1.y && y > p2.y) {
            if (p2.x == x) { Option(ON_LINE) } else { Option(CUTS_LINE) }
          } else {
            None
          }
        }
      } else if (horizontal) {
        if (y != p1.y) {
          None
        } else {
          if (p2.x > p1.x) {
            if (x >= p1.x && x < p2.x) {
              Option(ON_LINE)
            } else if (x < p1.x) {
              Option(CUTS_LINE)
            } else {
              None
            }
          } else {
            if (x > p2.x && x <= p1.x) {
              Option(ON_LINE)
            } else if (x < p2.x) {
              Option(CUTS_LINE)
            } else {
              None
            }
          }
        }
      } else {
        val gradient = (p2.y - p1.y) / (p2.x - p1.x)
        val intercept = p1.y - gradient * p1.x
        val intersectX = (y - intercept) / gradient

        if (x == intersectX) {
          Option(ON_LINE)
        } else if (x < intersectX) {
          if (p2.isAbove(p1)) {
            Option.when(y >= p1.y && y < p2.y)(CUTS_LINE)
          } else {
            Option.when(y <= p1.y && y > p2.y)(CUTS_LINE)
          }
        } else {
          None
        }
      }
    }
  }

  def horizontal: Boolean = p1.y == p2.y

  def vertical: Boolean = p1.x == p2.x

  def afterThis(block: GridBlock): Boolean = {
    block.x > Math.max(p1.x, p2.x)
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
      p1 = p1.copy(p1.x * scale, p1.y * scale),
      p2 = p2.copy(p2.x * scale, p2.y * scale)
    )
}

object ShapePointPair {

  def apply(pair: ((ShapePoint, ShapePoint), Int)): ShapePointPair =
    new ShapePointPair(pair._2, pair._1._1, pair._1._2)

  trait Intersection
  case object ON_LINE extends Intersection
  case object CUTS_LINE extends Intersection

}

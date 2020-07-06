package autofort.model.map

import java.lang.Math.abs

abstract class Point2D(val x: Int, val y: Int) {

  def touches(other: Point2D): Boolean =
    abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1 && !((x == other.x) && (y == other.y))

}

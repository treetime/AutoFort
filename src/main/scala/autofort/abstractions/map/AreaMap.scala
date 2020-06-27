package autofort.abstractions.map

import autofort.abstractions.map.AreaMap.XYMap


abstract class AreaMap[T](area: Vector[XYMap[T]]) {
  def get(z: Int): Option[XYMap[T]] = area.lift(z)

  def get(x: Int, y: Int, z: Int): Option[T] = for {
    level <- get(z)
    block <- level.get(x, y)
  } yield block
}

object AreaMap {
  case class XYMap[T](xy: Vector[Vector[T]]){
    def get(x: Int, y: Int): Option[T] = for {
      row <- xy.lift(x)
      point <- row.lift(y)
    } yield point

  }
}
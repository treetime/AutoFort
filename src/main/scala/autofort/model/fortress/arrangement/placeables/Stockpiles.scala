package autofort.model.fortress.arrangement.placeables

import autofort.model.fortress.arrangement.Placeable.Dimensions

object Stockpiles {

  case class Stockpile[T](x: Int, y: Int) extends Placeable(Dimensions(x, y))

}

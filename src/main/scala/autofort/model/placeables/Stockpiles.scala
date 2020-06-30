package autofort.model.placeables

import autofort.model.placeables.Placeable.Dimensions

object Stockpiles {

  case class Stockpile[T](x: Int, y: Int) extends Placeable

}

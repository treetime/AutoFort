package autofort.model.placeables

import autofort.model.fortress.arrangement.Placeable.Dimensions


object Workshops {
  case class Workshop[T]() extends Placeable(Dimensions(3,3))
}

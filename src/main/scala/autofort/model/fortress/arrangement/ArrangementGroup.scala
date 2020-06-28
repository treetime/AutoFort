package autofort.model.fortress.arrangement

import autofort.model.fortress.arrangement.ArrangementGroup.FunctionalSet
import autofort.model.fortress.arrangement.placeables.Furniture.{Table, Throne}
import autofort.model.fortress.arrangement.placeables.Placeable
import autofort.model.fortress.arrangement.placeables.Placeable.Dimensions

case class ArrangementGroup(components: FunctionalSet)

object ArrangementGroup {

  case class FunctionalSet(override val dim: Dimensions, priority: Int = 0) extends Placeable

  case class TableThrone(table: Table = Table(), throne: Throne = Throne())
      extends FunctionalSet(Dimensions(2, 1))

  case class DiningSet(sets: Seq[TableThrone] = Seq.empty)
      extends FunctionalSet(Dimensions(2, sets.length))

  object DiningSet {
    def apply(i: Int): DiningSet =
      new DiningSet((0 until i) map (_ => TableThrone()))
  }

}

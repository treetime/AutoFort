package autofort.abstractions.model.aesthetics.arrangement.placeables

import autofort.abstractions.model.aesthetics.arrangement.Placeable
import autofort.abstractions.model.aesthetics.arrangement.Placeable.Dimensions
import autofort.abstractions.model.aesthetics.arrangement.placeables.ArrangementGroup.FunctionalSet
import autofort.abstractions.model.aesthetics.arrangement.placeables.Furniture.{Table, Throne}

case class ArrangementGroup(components: FunctionalSet)

object ArrangementGroup {

  case class FunctionalSet(override val dim: Dimensions) extends Placeable

  case class TableSet(table: Table = Table(), throne: Throne = Throne()) extends FunctionalSet(Dimensions(2, 1))

  case class DiningSet(tableSets: Seq[TableSet]) extends FunctionalSet(Dimensions(2, tableSets.length))

}

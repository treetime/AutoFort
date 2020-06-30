package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{DOWN, Direction, UP}
import autofort.model.aesthetics.preferences.Alignment
import autofort.model.items.Item.{Table, Throne}
import autofort.model.items.PlacementGroup
import autofort.model.map.AreaDefinition

trait TableArrangement extends ArrangementConfig {
  def arrangeTables(tables: AreaDefinition): AreaDefinition = ???


}

object TableArrangement {

  trait CenterFillingStrategy extends TableArrangement

  case class DivideIntoParts(parts: Int, centered: Boolean = true)
      extends CenterFillingStrategy

  case class FixedSpacing(width: Int) extends CenterFillingStrategy
  case class Lines(alignment: Alignment) extends CenterFillingStrategy {

    def arrangeTables(area: AreaDefinition): AreaDefinition = {
      val min = area.center.largestRectangle.smallestDimension
      val max = area.center.largestRectangle.largestDimension
      (min, max) match {
        case (low, high) if low < 2 => if(high < 2 ) area else ???

      }
    }

  }

  trait PerimeterFillingStrategy extends TableArrangement
  case class SidesFirst(groupSize: Int, thickness: Double)
      extends PerimeterFillingStrategy

  def DiningGroup(rows: Int,
                  doubled: Boolean,
                  direction: Direction): PlacementGroup = {
    val thrones = PlacementGroup.fill(Throne, rows)
    val tables = PlacementGroup.fill(Table, rows)
    val group = thrones.area.attachTo(tables.area, UP)
    PlacementGroup(
      if (doubled) group.attachTo(group.flipVertical, DOWN) else group
    )
  }

}

package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{
  DOWN,
  LEFT,
  UP
}
import autofort.model.aesthetics.preferences.Orientation.{HORIZONTAL, VERTICAL}
import autofort.model.aesthetics.preferences.{Alignment, Orientation}
import autofort.model.items.Item.{Table, Throne}
import autofort.model.items.PlacementGroup
import autofort.model.map.AreaDefinition

trait TableArrangement extends ArrangementConfig {
  def fill(area: AreaDefinition, spec: Double): AreaDefinition
}

object TableArrangement {

  def DiningGroup(rows: Int,
                  doubled: Boolean,
                  orientation: Orientation): PlacementGroup = {
    val thrones = PlacementGroup.fill(Throne, rows)
    val tables = PlacementGroup.fill(Table, rows)
    val group = thrones.area.attachTo(tables.area, UP)
    val items = if (doubled) group.attachTo(group.flipVertical, DOWN) else group
    PlacementGroup(orientation match {
      case HORIZONTAL => items.rotate(LEFT)
      case VERTICAL   => items
    })
  }

  trait CenterFillingStrategy extends TableArrangement

  trait PerimeterFillingStrategy extends ArrangementConfig
  //case class SidesFirst(groupSize: Int, thickness: Double)
  //    extends PerimeterFillingStrategy

  /* case class DivideIntoParts(parts: Int, centered: Boolean = true)
      extends CenterFillingStrategy {


  }

//  case class FixedSpacing(width: Int) extends CenterFillingStrategy*/
  case class Lines(alignment: Alignment) extends CenterFillingStrategy {

    def fill(area: AreaDefinition, fill: Double): AreaDefinition = { //@todo should be room def
      val quantity = Math.round(area.size * fill / 2).toInt
      val subArea = fill * area.size
      val fillArea = area.shrinkHorizontallyTo(fill)
      val items = DiningGroup(fillArea.w, fillArea.h > 4, HORIZONTAL)
      AreaDefinition(area.replaceStartingLeftTop(items.area))
    }

  }

}

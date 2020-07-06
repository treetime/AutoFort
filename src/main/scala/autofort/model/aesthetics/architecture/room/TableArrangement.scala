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

trait TableArrangement extends ArrangementConfig {
  def fill(area: RectangularArea, spec: Double): RectangularArea
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

    def fill(area: RectangularArea, fill: Double): RectangularArea = {
      val fillArea = area.scaleTo(fill)
      val items = DiningGroup(fillArea.width, fillArea.height > 4, HORIZONTAL)
      RectangularArea(area.replaceStartingLeftTop(items.area))
    }

  }

}

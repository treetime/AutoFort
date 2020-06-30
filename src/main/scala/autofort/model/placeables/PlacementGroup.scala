package autofort.model.placeables

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.map.{AreaDefinition, GridBlock}

case class PlacementGroup(override val area: Set[GridBlock] = Set(GridBlock()))
    extends AreaDefinition(area) {

  def withItemAt(placeable: Placeable, x: Int, y: Int): PlacementGroup = {
    copy(area = area + GridBlock().move(x, y).withPlaceable(placeable))
  }

  def flipXY: PlacementGroup = copy(area.map(_.transpose))

  def flipVertical: PlacementGroup =
    copy(area.map(_.flipVertical(yMax).move(0, -yMax)))

  def attachTo(other: PlacementGroup, direction: Direction = RIGHT): PlacementGroup =
    direction match {
      case UP    => copy(zeroAt(other.xMin, other.yMax))
      case RIGHT => copy(zeroAt(other.xMax, other.yMin))
      case LEFT  => other.attachTo(this, RIGHT)
      case DOWN  => other.attachTo(this, UP)
    }

  def zeroAt(x: Int, y: Int): Set[GridBlock] = {
    area.map(_.move(x - min(_.x), y - min(_.y)))
  }

}

object PlacementGroup {

  def apply(placeable: Placeable): PlacementGroup =
    PlacementGroup().withItemAt(placeable, 0, 0)

  def fill(placeable: Placeable, x: Int, y: Int): PlacementGroup =
    PlacementGroup(
      (0 until x)
        .flatMap(px => {
          (0 until y).map(py => GridBlock(px, py))
        })
        .toSet
    )

}

package autofort.model.placeables
import autofort.model.aesthetics.architecture.shape.ShapeDefinition.DOWN
import autofort.model.placeables.Placeable._
object Furniture {

  val throneTable: PlacementGroup = PlacementGroup()
    .withItemAt(Throne, 0, 0)
    .withItemAt(Table, 0, 1)

  val throneTableTableThrone: PlacementGroup =
    throneTable.attachTo(throneTable.flipVertical, DOWN)

  def tableGroup(rows: Int): PlacementGroup = {
    (0 until rows).foldLeft(PlacementGroup())(
      (a, _) => throneTableTableThrone.attachTo(a)
    )
  }

}

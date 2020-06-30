package autofort.model.items
import autofort.model.aesthetics.architecture.shape.ShapeDefinition.{
  DOWN,
  Direction,
  UP
}
import autofort.model.items.Item._

object Furniture {

  //
  //rows: Int,
  //                  doubled: Boolean,

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

  def Workshop(x: Int, y: Int): PlacementGroup =
    PlacementGroup.fill(WorkshopTile, x, y)

}

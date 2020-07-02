package autofort.model.items

import autofort.model.map.{AreaDefinition, GridBlock}

case class PlacementGroup(area: AreaDefinition = AreaDefinition.empty) {

  def withItemAt(placeable: Item, x: Int, y: Int): PlacementGroup = {
    copy(area = area.withBlock(GridBlock().move(x, y).withItem(placeable)))
  }

}

object PlacementGroup {

  def apply(placeable: Item): PlacementGroup =
    PlacementGroup().withItemAt(placeable, 0, 0)

  def fill(item: Item, x: Int, y: Int = 0): PlacementGroup =
    PlacementGroup(
      AreaDefinition(
        (0 until x)
          .flatMap(px => {
            (0 until y).map(py => GridBlock(px, py, 0).withItem(item))
          })
          .toSet
      )
    )

}

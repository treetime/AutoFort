package autofort.abstractions.map

import autofort.abstractions.map.GridBlock.{FloorTile, Point, WallTile}
import autofort.abstractions.map.GridMap.XYDimensions
import autofort.abstractions.model.aesthetics.arrangement.Placeable
import autofort.abstractions.model.aesthetics.materials.Material

case class GridBlock(location: Point,
                     floor: Option[FloorTile] = None,
                     wall: Option[WallTile] = None,
                     placeable: Option[Placeable] = None)
    extends XYDimensions(location.x, location.y) {
  def withFloor(floorTile: Option[FloorTile]): GridBlock =
    copy(floor = floorTile)
  def withWall(wallTile: Option[WallTile]): GridBlock = copy(wall = wallTile)
  def withPlaceable(placeableTile: Option[Placeable]): GridBlock =
    copy(placeable = placeableTile)

}

object GridBlock {
  def apply(x: Int = 0, y: Int = 0, z: Int = 0): GridBlock =
    new GridBlock(Point(x, y, z))

  case class Point(x: Int, y: Int, z: Int)

  case class FloorTile(material: Option[Material])

  case class WallTile(material: Option[Material])
}

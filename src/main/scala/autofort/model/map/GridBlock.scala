package autofort.model.map

import autofort.model.placeables.Placeable
import autofort.model.map.GridBlock.{FloorTile, Point, WallTile}
import autofort.model.map.GridMap.XYDimensions
import autofort.model.aesthetics.materials.Material

case class GridBlock(location: Point,
                     floor: Option[FloorTile] = None,
                     wall: Option[WallTile] = None,
                     placeable: Option[Placeable] = None)
    extends XYDimensions {
  val x: Int = location.x
  val y: Int = location.y
  def withFloor(floorTile: Option[FloorTile]): GridBlock =
    copy(floor = floorTile)
  def withWall(wallTile: Option[WallTile]): GridBlock = copy(wall = wallTile)
  def withPlaceable(placeableTile: Option[Placeable]): GridBlock =
    copy(placeable = placeableTile)

  def moveX(i: Int): GridBlock = copy(location = location.copy(x = x + i))
  def moveY(i: Int): GridBlock = copy(location = location.copy(y = y + i))



}

object GridBlock {
  def apply(x: Int = 0, y: Int = 0, z: Int = 0): GridBlock =
    new GridBlock(Point(x, y, z))

  case class Point(x: Int, y: Int, z: Int)

  case class FloorTile(material: Option[Material])

  case class WallTile(material: Option[Material])
}

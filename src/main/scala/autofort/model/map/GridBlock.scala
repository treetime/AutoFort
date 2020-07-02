package autofort.model.map

import java.lang.Math.{abs, pow, sqrt}

import autofort.model.aesthetics.architecture.room._
import autofort.model.aesthetics.materials.Stone.Granite
import autofort.model.items.Item
import autofort.model.map.WallTile.Point

case class GridBlock(location: Point = Point(0,0,0),
                     floor: Option[FloorTile] = Some(FloorTile(Granite())),
                     wall: Option[WallTile] = None,
                     placeable: Option[Item] = None) {
  lazy val r: Double = sqrt(pow(x, 2) + pow(y, 2))
  val x: Int = location.x
  val y: Int = location.y

  def transpose: GridBlock = copy(location = location.transpose)

  def flipVertical(max: Int): GridBlock =
    copy(location = location.copy(y = max - location.y))

  def flipHorizontal(max: Int): GridBlock =
    copy(location = location.copy(x = max - location.x))

  def withFloor(floorTile: Option[FloorTile]): GridBlock =
    copy(floor = floorTile)
  def withWall(wallTile: Option[WallTile]): GridBlock = copy(wall = wallTile)
  def withItem(item: Item): GridBlock =
    copy(placeable = Option(item))

  def move(dx: Int = 0, dy: Int = 0, dz: Int = 0): GridBlock =
    copy(location = location.move(dx, dy, dz))

  def classifyIn(area: AreaDefinition): PerimeterBlockType = {
    if (isInternalCornerOf(area)) {
      InternalCorner
    } else if (isExternalCornerOf(area)) {
      ExternalCorner
    } else if (isDetachableFromPerimeterOf(area)) {
      Detachable
    } else {
      Normal
    }
  }

  def isInternalCornerOf(area: AreaDefinition): Boolean =
    countNeighborsIn(area) == 7

  def isExternalCornerOf(area: AreaDefinition): Boolean =
    getNeighborsIn(area.perimeter, !_.isDetachableFromPerimeterOf(area)).size == 2

  def isDetachableFromPerimeterOf(area: AreaDefinition): Boolean =
    area.perimeter.contains(this) && (area.perimeter - this).isContinuous

  def countNeighborsIn(area: AreaDefinition,
                       f: GridBlock => Boolean = (x => true)): Int = {
    getNeighborsIn(area).size
  }

  def getNeighborsIn(area: AreaDefinition,
                     f: GridBlock => Boolean = (x => true)): Set[GridBlock] = {
    area.area.filter(b => this.touches(b) && f(b))
  }

  def touches(other: GridBlock): Boolean = {
    abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1 && !((x == other.x) && (y == other.y))
  }

  override def toString: String = {
    wall.map(_.toString).orElse(placeable.map(_.toString)).orElse(floor.map(_.toString)).getOrElse(" ")
  }
}

object GridBlock {

  def apply(x: Int, y: Int, z: Int): GridBlock = GridBlock(Point(x,y, z))

}

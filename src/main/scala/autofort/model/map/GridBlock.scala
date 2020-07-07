package autofort.model.map

import java.lang.Math.{abs, pow, sqrt}

import autofort.model.aesthetics.architecture.room._
import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.materials.Stone.Granite
import autofort.model.items.Item

case class GridBlock(x: Int = 0,
                     y: Int = 0,
                     z: Int = 0,
                     floor: Option[FloorTile] = Some(FloorTile(Granite())),
                     wall: Option[WallTile] = None,
                     placeable: Option[Item] = None) {
  lazy val r: Double = sqrt(pow(x, 2) + pow(y, 2))

  def move(dx: Int, dy: Int, dz: Int = 0): GridBlock =
    copy(x = x + dx, y = y + dy, z = z + dz)

  def volumeCompare(other: GridBlock): Boolean = {
    areaCompare(other) && z == other.z
  }

  def areaCompare(other: GridBlock): Boolean = {
    x == other.x &&
    y == other.y
  }

  def transpose: GridBlock = copy(x = y, y = x)

  def flipVertical(max: Int): GridBlock =
    copy(y = max - y)

  def flipHorizontal(max: Int): GridBlock =
    copy(x = max - x)

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
    area.blocks.filter(b => this.touches(b) && f(b))
  }

  def touches(other: GridBlock): Boolean = {
    val xDist = x - other.x
    val yDist = y - other.y
    abs(xDist) <= 1 && Math.abs(yDist) <= 1 && !areaCompare(other)
  }

  def cardinalDirection(other: GridBlock): Option[Direction] = {
    (x - other.x, y - other.y) match {
      case (-1, 0) => Option(LEFT)
      case (1, 0)  => Option(RIGHT)
      case (0, -1) => Option(UP)
      case (0, 1) => Option(DOWN)
      case _       => None
    }
  }

  def withFloorTile(floorTile: Option[FloorTile]): GridBlock =
    copy(floor = floorTile)
  def withWall(wallTile: Option[WallTile]): GridBlock = copy(wall = wallTile)
  def withItem(item: Item): GridBlock =
    copy(placeable = Option(item))

  override def toString: String = {
    wall
      .map(_.toString)
      .orElse(placeable.map(_.toString))
      .orElse(floor.map(_.toString))
      .getOrElse(" ")
  }
}

object GridBlock {}

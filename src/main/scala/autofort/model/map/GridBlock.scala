package autofort.model.map

import java.lang.Math.{abs, pow, sqrt}

import autofort.model.aesthetics.architecture.room._
import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.materials.Stone.Granite
import autofort.model.aesthetics.preferences.Alignment
import autofort.model.items.Item

case class GridBlock(x: Int = 0,
                     y: Int = 0,
                     z: Int = 0,
                     floor: Option[FloorTile] = Some(FloorTile(Granite())),
                     wall: Option[WallTile] = None,
                     placeable: Option[Item] = None,
                     classification: Option[GridBlockClassification] = None) {
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

  def classifyIn(area: AreaDefinition): GridBlock = {
    copy(classification = Option(getClassificationIn(area)))
  }

  def getClassificationIn(area: AreaDefinition): GridBlockClassification = {
    val (walls, unwalled) =
      if (area.perimeter.blocks.headOption.exists(_.wall.isDefined)) {
        (area.perimeter, area.center)
      } else {
        (new AreaDefinition(), area)
      }
    if (unwalled.center.contains(this)) {
      //AreaBlockType(0)
      classifyInCenter(unwalled)
    } else if (walls.contains(this)) {
      WallBlockType
    } else {
      classifyInPerimeter(unwalled)
    }
  }

  def classifyInCenter(area: AreaDefinition): AreaBlockType = {
    val areas = area.center.subSpaces.rectangularAreas.groupBy(_.area)
    val sizes = areas.keys.toSeq.sorted.reverse
    areas
      .collectFirst {
        case (size, spaces) if spaces.exists(_.contains(this)) =>
          AreaBlockType(sizes.indexOf(size) + 1)
      }
      .getOrElse(AreaBlockType(0))
  }

  def classifyInPerimeter(area: AreaDefinition): PerimeterBlockType = {
    if (isInternalCornerOf(area)) {
      InternalCorner
    } else if (isExternalCornerOf(area)) {
      ExternalCorner
    } else if (isDetachable(area)) {
      Detachable
    } else {
      PerimeterBlock
    }
  }

  def isInternalCornerOf(area: AreaDefinition): Boolean = {
    getNeighborsIn(area, !_.isDetachable(area)).size == 7
  }

  def isExternalCornerOf(area: AreaDefinition): Boolean = {
    val neighbors = getNeighborsIn(area.perimeter, !_.isDetachable(area))
    val dirs = neighbors.flatMap(cardinalDirection)
    dirs.size == 2 && dirs
      .map(Alignment.fromDirection)
      .size == 2
  }

  def isDetachable(area: AreaDefinition): Boolean =
    countNeighborsIn(area.center) == 0

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
      case (0, 1)  => Option(DOWN)
      case _       => None
    }
  }

  def withFloorTile(floorTile: Option[FloorTile]): GridBlock =
    copy(floor = floorTile)
  def withWall(wallTile: Option[WallTile]): GridBlock = copy(wall = wallTile)
  def withItem(item: Item): GridBlock =
    copy(placeable = Option(item))

/*  override def toString: String = {
    classification
      .map {
        case WallBlockType    => wall.map(_.toString).getOrElse("?")
        case AreaBlockType(n) => (n%10).toString
        case PerimeterBlock   => floor.map(_.toString).getOrElse("?")
        case InternalCorner   => floor.map(_.toString).getOrElse("?")
        case ExternalCorner   => floor.map(_.toString).getOrElse("?")
        case Detachable =>
          floor
            .map(_.toString)
            .getOrElse("?")
        case _ => floor.map(_.toString).getOrElse("?")
      }
      .getOrElse {
        wall
          .map(_.toString)
          .orElse(placeable.map(_.toString))
          .orElse(floor.map(_.toString))
          .getOrElse(" ")
      }
  }*/
    override def toString: String = {
    classification
      .map {
        case WallBlockType => wall.map(_.toString).getOrElse("?")
        case AreaBlockType(n) => Console.BLACK + GridBlock.backgroundPriorities.lift(n).getOrElse("") +  n.toString
        case PerimeterBlock =>  Console.YELLOW + floor.map(_.toString).getOrElse("?")
        case InternalCorner =>  Console.CYAN + floor.map(_.toString).getOrElse("?")
        case ExternalCorner =>  Console.RED + floor.map(_.toString).getOrElse("?")
        case Detachable =>      Console.WHITE + floor
          .map(_.toString)
          .getOrElse("?")
        case _ => floor.map(_.toString).getOrElse("?")
      }
      .getOrElse {
        wall
          .map(_.toString)
          .orElse(placeable.map(_.toString))
          .orElse(floor.map(_.toString))
          .getOrElse(" ")
      }

  }
}

object GridBlock {

  val backgroundPriorities = Seq(
    Console.BLACK_B,
    Console.WHITE_B,
    Console.YELLOW_B,
    Console.RED_B,
    Console.MAGENTA_B,
    Console.CYAN_B,
    Console.GREEN_B,
    Console.BLUE_B
  )

}

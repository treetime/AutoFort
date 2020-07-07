package autofort.model.aesthetics.architecture.room

import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.map.{GridBlock, WallTile}

class AreaDefinition(val blocks: Set[GridBlock] = Set.empty) {

  lazy val xBlocks: Set[Int] = blocks.map(_.x)
  lazy val yBlocks: Set[Int] = blocks.map(_.y)
  lazy val width: Int = xMax - xMin
  lazy val height: Int = yMax - yMin
  lazy val (maxDimension: Int, minDimension: Int) =
    if (width > height) (width, height) else (height, width)
  lazy val area: Int = blocks.size
  lazy val xMin: Int = xBlocks.minOption.getOrElse(0)
  lazy val xMax: Int = xBlocks.maxOption.getOrElse(0)
  lazy val yMin: Int = yBlocks.minOption.getOrElse(0)
  lazy val yMax: Int = yBlocks.maxOption.getOrElse(0)
  lazy val (center, perimeter) = blocks.partition(isCenter) match {
    case (c, p) => (new AreaDefinition(c), new AreaDefinition(p))
  }

  lazy val subSpaces: SubSpaces = SubSpaces.fromArea(this)

  def attachTo(other: AreaDefinition,
               direction: Direction = RIGHT): AreaDefinition =
    direction match {
      case UP    => new AreaDefinition(zeroAt(other.xMin, other.yMax))
      case RIGHT => new AreaDefinition(zeroAt(other.xMax, other.yMin))
      case LEFT  => other.attachTo(this, RIGHT)
      case DOWN  => other.attachTo(this, UP)
    }

  def zeroAt(x: Int, y: Int): Set[GridBlock] = {
    blocks.map(_.move(x - xMin, y - yMin))
  }

  def -(that: AreaDefinition): AreaDefinition = {
    //assert(this contains that, s"can't remove what you don't have. ( ${this.blocks diff blocks}")
    new AreaDefinition(blocks diff that.blocks)
  }

  def contains(other: AreaDefinition): Boolean = other.blocks.subsetOf(blocks)

  def -(that: GridBlock): AreaDefinition = {
    assert(this contains that, "can't remove what you don't have.")
    new AreaDefinition(blocks - that)
  }

  def contains(b: GridBlock): Boolean = blocks(b)

  def containsArea(area: AreaDefinition): Boolean =
    area.blocks.forall(areaContains)

  def areaContains(b: GridBlock): Boolean = blocks.exists(_.areaCompare(b))

  def isContinuous: Boolean = {
    blocks.forall(b => b.countNeighborsIn(perimeter) >= 2)
  }

  def withBlock(block: GridBlock): AreaDefinition =
    new AreaDefinition(blocks + block)

  def rotate(direction: Direction): AreaDefinition = direction match {
    case LEFT  => flipXY.flipVertical
    case RIGHT => flipXY.flipHorizontal
    case DOWN  => flipVertical.flipHorizontal
    case UP    => flipVertical.flipHorizontal
  }

  def flipVertical: AreaDefinition =
    new AreaDefinition(blocks.map(_.flipVertical(yMax).move(0, -yMax)))

  def flipHorizontal: AreaDefinition =
    new AreaDefinition(blocks.map(_.flipHorizontal(xMax).move(-xMax, 0)))

  def flipXY: AreaDefinition = new AreaDefinition(blocks.map(_.transpose))

  def replaceStartingLeftTop(other: AreaDefinition): Set[GridBlock] = {
    val dx = leftTop.x - other.leftTop.x
    val dy = leftTop.y - other.leftTop.y
    replaceWith(other.move(dx, dy))
  }

  def move(x: Int, y: Int): AreaDefinition =
    new AreaDefinition(blocks.map(b => b.move(x, y)))

  def leftTop: GridBlock = blocks.minBy(_.r)

  def replaceWith(other: AreaDefinition): Set[GridBlock] = {
    blocks.map { b =>
      other.blocks.find(_.areaCompare(b)) match {
        case Some(friend) => friend
        case None         => b
      }
    }
  }

  override def toString: String = {
    val classified = blocks.map(_.classifyIn(this))
    (0 to yMax)
      .map { py =>
        (0 to xMax)
          .map { px =>
            classified.find(_.areaCompare(GridBlock(px, py, 0))) match {
              case Some(friend) => friend.toString
              case None         => "."
            }
          }
          .mkString("") + s" $py"
      }
      .mkString("\n")
  }

  def withWalls(): AreaDefinition = {
    val offsetSet = move(1, 1)
    val walls = (yMin to yMax + 2).flatMap { py =>
      (xMin to xMax + 2).flatMap { px =>
        val block = GridBlock(px, py)
        Option.when(!offsetSet.areaContains(block) && offsetSet.touches(block))(block) //cardinal neighbors
      }
    }

    val wallBlocks =
    walls.foldLeft(Seq.empty[GridBlock]) {
      case (acc, elem) =>
        val direction = walls.flatMap(_.cardinalDirection(elem)).toSet
      acc :+ elem.withWall(Option(WallTile.fromConnections(direction)))
    }

    new AreaDefinition(offsetSet.blocks ++ wallBlocks)

  }

  def touches(b: GridBlock): Boolean = blocks.exists(_.touches(b))

  private def isCenter(b: GridBlock): Boolean = {
    getNeighbors(b).size == 8
  }

  def getNeighbors(block: GridBlock): Set[GridBlock] = {
    blocks.filter(b => block.touches(b))
  }

}

object AreaDefinition {}

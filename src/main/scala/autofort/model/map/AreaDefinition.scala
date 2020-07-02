package autofort.model.map

import autofort.model.aesthetics.architecture.room.{Detachable, ExternalCorner, InternalCorner, Normal}
import autofort.model.aesthetics.architecture.shape.{ShapeDefinition, ShapePointPair}
import autofort.model.aesthetics.architecture.shape.ShapeDefinition._
import autofort.model.aesthetics.preferences.Orientation
import autofort.model.aesthetics.preferences.Orientation.{HORIZONTAL, VERTICAL}
import autofort.model.map.AreaDefinition.{CenterProfile, PerimeterProfile, SubSpaces}

class AreaDefinition(val area: Set[GridBlock]) {

  lazy val w: Int = dist(_.x)
  lazy val h: Int = dist(_.y)
  lazy val xMax: Int = max(_.x)
  lazy val yMax: Int = max(_.y)
  lazy val xMin: Int = min(_.x)
  lazy val yMin: Int = min(_.y)

  lazy val orientation: Orientation = if (w > h) HORIZONTAL else VERTICAL

  lazy val largestDimension: Int = Math.max(w, h)
  lazy val smallestDimension: Int = Math.min(w, h)
  lazy val (center, perimeter) =
    area.partition(block => block.countNeighborsIn(this) == 8) match {
      case (cent, peri) => (AreaDefinition(cent), AreaDefinition(peri))
    }
  lazy val profilePerimeter: PerimeterProfile =
    PerimeterProfile.fromAreaDefinition(this)
  lazy val centerProfile: CenterProfile = CenterProfile(center.subSpaces)
  lazy val largestRectangle: AreaDefinition = area
    .map(findLargestRectangle)
    .groupBy(_.area.size)
    .maxBy(_._1)
    ._2
    .minBy(_.leftTop.r) //favour things that are more left, then more top

  lazy val largestSquare: AreaDefinition = area
    .map(findLargestSquare)
    .groupBy(_.area.size)
    .maxBy(_._1)
    ._2
    .minBy(_.leftTop.r) //todo min by distance from center

  def -(that: GridBlock): AreaDefinition = {
    assert(area(that), "can't remove what it does not contain")
    AreaDefinition(area - that)
  }

  def isSquare: Boolean =
    isRectangular && (largestRectangle.w == largestRectangle.h)

  def isRectangular: Boolean = largestRectangle.contains(this)

  def contains(block: GridBlock): Boolean = area(block)

  def isContinuous: Boolean = {
    perimeter.area.forall(b => b.countNeighborsIn(perimeter) >= 2)
  }

  def subSpaces: SubSpaces = {
    @scala.annotation.tailrec
    def breakIntoComponents(
      source: AreaDefinition = this,
      components: Set[AreaDefinition] = Set.empty
    ): Set[AreaDefinition] = {
      if (source.isEmpty) {
        components
      } else {
        breakIntoComponents(
          source - source.largestRectangle,
          components + source.largestRectangle
        )
      }
    }
    SubSpaces(breakIntoComponents())
  }

  def -(that: AreaDefinition): AreaDefinition = {
    assert(this contains that, "can't remove what you don't have.")
    AreaDefinition(area diff that.area)
  }

  def contains(that: AreaDefinition): Boolean = that.area subsetOf this.area

  def isEmpty: Boolean = area.isEmpty

  def min(f: GridBlock => Int): Int = f(area.minBy(f))

  def max(f: GridBlock => Int): Int = f(area.maxBy(f))

  def attachTo(other: AreaDefinition,
               direction: Direction = RIGHT): AreaDefinition =
    direction match {
      case UP    => new AreaDefinition(zeroAt(other.xMin, other.yMax))
      case RIGHT => new AreaDefinition(zeroAt(other.xMax, other.yMin))
      case LEFT  => other.attachTo(this, RIGHT)
      case DOWN  => other.attachTo(this, UP)
    }

  def zeroAt(x: Int, y: Int): Set[GridBlock] = {
    area.map(_.move(x - min(_.x), y - min(_.y)))
  }

  def rotate(direction: Direction): AreaDefinition = direction match {
    case LEFT  => flipXY.flipVertical
    case RIGHT => flipXY.flipHorizontal
    case DOWN  => flipVertical.flipHorizontal
    case UP    => flipVertical.flipHorizontal
  }

  def flipVertical: AreaDefinition =
    new AreaDefinition(area.map(_.flipVertical(yMax).move(0, -yMax)))

  def flipHorizontal: AreaDefinition =
    new AreaDefinition(area.map(_.flipHorizontal(xMax).move(-xMax, 0)))

  def flipXY: AreaDefinition = new AreaDefinition(area.map(_.transpose))

  def withBlock(block: GridBlock): AreaDefinition =
    new AreaDefinition(area + block)

  def replaceStartingLeftTop(other: AreaDefinition): Set[GridBlock] = {
    val dx = leftTop.x - other.leftTop.x
    val dy = leftTop.y - other.leftTop.y
    replaceWith(other.move(dx, dy))
  }

  def leftTop: GridBlock = area.minBy(_.r)

  def replaceWith(other: AreaDefinition): Set[GridBlock] = {
    area.map { b =>
      other.area.find(_.location == b.location) match {
        case Some(block) => block
        case _           => b
      }
    }
  }

  def shrinkHorizontallyTo(fraction: Double): AreaDefinition = {
    val bracket = Math.round(w * (1 - fraction) / 2).toInt
    AreaDefinition(area.filter(b => b.x >= bracket && b.x <= w - bracket))
  }

  override def toString: String = {
    (0 until h)
      .map { py =>
        (0 until w)
          .map { px =>
            area
              .find(b => b.x == px && b.y == py)
              .map(_.toString)
              .getOrElse(" ")
          }
          .mkString("")
      }
      .mkString("\n")
  }

  def getSurroundingBlocks: AreaDefinition = {
    val offset = move(1, 1)
    AreaDefinition(AreaDefinition.blockField(w + 2, h + 2).filterNot(area))
  }

  def move(x: Int, y: Int): AreaDefinition =
    new AreaDefinition(area.map(b => b.move(x, y)))

  private def dist(f: GridBlock => Int) =
    if (area.nonEmpty) {
      f(area.maxBy(f)) - f(area.minBy(f))
    } else {
      0
    }

  private def findLargestRectangle(block: GridBlock): AreaDefinition = {
    @scala.annotation.tailrec
    def findLargest(
      block: GridBlock,
      x: Int = 1,
      y: Int = 1,
      currentLargest: AreaDefinition = AreaDefinition.empty
    ): AreaDefinition = {
      getRectangle(block, x, y) match {
        case Some(thing) if thing > currentLargest =>
          findLargest(block, x + 1, y, thing)
        case None =>
          if (x == 1) currentLargest else findLargest(block, 1, y + 1)
        case _ => findLargest(block, x + 1, y, currentLargest)
      }
    }
    findLargest(block)
  }

  def >(that: AreaDefinition): Boolean =
    this.area.size > that.area.size

  private def getRectangle(start: GridBlock,
                           x: Int,
                           y: Int): Option[AreaDefinition] = {
    val blocks = (0 until x).flatMap { px =>
      (0 until y).map { py =>
        start.move(px, py)
      }
    }.toSet
    Option.when(blocks.subsetOf(area))(AreaDefinition(blocks))
  }

  private def findLargestSquare(block: GridBlock): AreaDefinition = {
    area
      .map { block =>
        (0 until smallestDimension)
          .flatMap(i => getRectangle(block, i, i))
          .maxBy(_.size)
      }
      .maxBy(_.size)
  }

  def size: Int = area.size

}

object AreaDefinition {

  def empty: AreaDefinition = AreaDefinition(Set.empty)

  /**
    * Creates a room where the shortest side of the largest rectangle in the room is "scale"
    * */
  def fromShape(shape: ShapeDefinition, scale: Int): AreaDefinition = {
    val scalingFactor = 100
    val bigArea = createAreaDefinition(shape, scalingFactor)
    val scaledScale = Math
      .round(scale * (bigArea.smallestDimension / scalingFactor.toDouble))
      .toInt
    createAreaDefinition(shape, scaledScale)
  }

  private def createAreaDefinition(shape: ShapeDefinition,
                                   scale: Int): AreaDefinition = {
    val xMax = Math.round(shape.xMax * scale).toInt
    val yMax = Math.round(shape.yMax * scale).toInt
    val pairs = shape.pairs //gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(xMax, yMax).filter { block =>
      pairs.exists(_.pairContains(block, scale))
    }
    AreaDefinition(validBlocks)
  }

  def apply(area: Set[GridBlock] = Set.empty) = new AreaDefinition(area)

  def blockField(xMax: Int, yMax: Int): Set[GridBlock] = {
    (0 until xMax).flatMap { px =>
      (0 until yMax).map { py =>
        GridBlock(px, py, 0)
      }
    }.toSet
  }

  case class SubSpaces(rectangles: Map[Int, Set[AreaDefinition]]) {

    def orderedList: IndexedSeq[(Int, Set[AreaDefinition])] =
      rectangles.toIndexedSeq.sortBy(_._1)

    /*  def map(f: ((Int, AreaDefinition)) => (Int, AreaDefinition)): SubSpaces =
      copy(rectangles = rectangles.map((size, definitions) => size -> definitions.map(f)).toMap)*/

  }

  case class PerimeterProfile(internal: Set[GridBlock],
                              external: Set[GridBlock],
                              detachable: Set[GridBlock],
                              lineSpaces: SubSpaces)

  case class CenterProfile(centerSpaces: SubSpaces)

  object PerimeterProfile {

    def fromAreaDefinition(areaDefinition: AreaDefinition): PerimeterProfile = {
      val profile = areaDefinition.perimeter.area
        .groupBy(_.classifyIn(areaDefinition))
        .withDefault(_ => Set.empty[GridBlock])

      PerimeterProfile(
        profile(InternalCorner),
        profile(ExternalCorner),
        profile(Detachable),
        AreaDefinition(profile(Normal)).subSpaces
      )
    }
  }

  object SubSpaces {

    def apply(items: Set[AreaDefinition]): SubSpaces =
      new SubSpaces(items.groupBy(_.size))

    // def scaleRoom(reference: SubSpaces) = ???

  }

}

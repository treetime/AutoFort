package autofort.model.aesthetics.architecture.shape

import autofort.model.aesthetics.architecture.room.TableArrangement.{
  Detachable,
  ExternalCorner,
  InternalCorner,
  Normal
}
import autofort.model.aesthetics.architecture.shape.AreaDefinition.{
  CenterProfile,
  PerimeterProfile,
  SubSpaces
}
import autofort.model.aesthetics.preferences.Shapeliness
import autofort.model.aesthetics.preferences.Shapeliness.{FAT, SQUARE, THIN}
import autofort.model.map.GridBlock

case class AreaDefinition(area: Set[GridBlock],
                          val rectangular: Boolean = false) {

  lazy val w: Int = dist(_.x)
  lazy val h: Int = dist(_.y)
  lazy val shapeliness: Shapeliness =
    if (w > h) FAT else if (w < h) THIN else SQUARE
  lazy val maxDimension: Int = Math.max(w, h)
  lazy val minDimension: Int = Math.min(w, h)
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

  def -(that: GridBlock): AreaDefinition = {
    assert(area(that), "can't remove what it does not contain")
    copy(area = area - that)
  }

  def isSquare: Boolean =
    isRectangular && (largestRectangle.w == largestRectangle.h)

  def isRectangular: Boolean = largestRectangle.contains(this)

  def contains(block: GridBlock): Boolean = area(block)

  def size: Int = area.size

  def isContinuous: Boolean = {
    perimeter.area.forall(b => b.countNeighborsIn(perimeter) >= 2)
  }

  def leftTop: GridBlock = area.minBy(_.r)

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
    copy(area = area diff that.area)
  }

  def contains(that: AreaDefinition): Boolean = that.area subsetOf this.area

  def isEmpty: Boolean = area.isEmpty

  private def dist(f: GridBlock => Int) = f(area.maxBy(f)) - f(area.minBy(f))

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

}

object AreaDefinition {

  def empty: AreaDefinition = AreaDefinition(Set.empty)

  def fromShape(shape: ShapeDefinition, scale: Int): AreaDefinition = {
    val xMax = Math.round(shape.xMax * scale).toInt
    val yMax = Math.round(shape.yMax * scale).toInt
    val conditions = shape.asConditions(scale) //gives rules to determine whether a block is inside or outside the shape
    val validBlocks = blockField(xMax, yMax).filter { block =>
      conditions.forall(_.holds(block.x, block.y))
    }
    AreaDefinition(validBlocks)
  }

  def blockField(xMax: Int, yMax: Int): Set[GridBlock] = {
    (0 until xMax).flatMap { px =>
      (0 until yMax).map { py =>
        GridBlock(px, py)
      }
    }.toSet
  }

  private def apply(area: Set[GridBlock],
                    rectangular: Boolean): AreaDefinition =
    new AreaDefinition(area, rectangular)

  case class SubSpaces(rectangles: Map[Int, AreaDefinition]) {

    def map(f: ((Int, AreaDefinition)) => (Int, AreaDefinition)): SubSpaces =
      copy(rectangles = rectangles.toSeq.map(f).toMap)

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

    def scaleRoom(reference: SubSpaces)

  }

}

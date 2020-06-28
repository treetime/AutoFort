package autofort.model.aesthetics.architecture.shape

import autofort.model.map.GridBlock
import autofort.model.aesthetics.architecture.shape.AreaDefinition.SubSpaces

case class AreaDefinition(area: Set[GridBlock]) {

  lazy val w: Int = {
    val xs = area.map(_.x)
    xs.max - xs.min
  }

  lazy val h: Int = {
    val ys = area.map(_.y)
    ys.max - ys.min
  }

  lazy val maxDimension: Int = Math.max(w, h)

  def isRectangular: Boolean = w == h

  lazy val (center, perimeter) = area.partition { gridBlock =>
    area.count(b => b.touches(gridBlock)) == 8
  } match {
    case (cent, peri) => (AreaDefinition(cent), AreaDefinition(peri))
  }

  def size: Int = area.size

  def largestSquare: Int = {
    (0 until maxDimension).map(countSquares).zipWithIndex.maxBy(_._1)._2
  }

  def countSquares(sideLength: Int): Int = {
    area.count { block: GridBlock =>
      (0 until sideLength).forall { h =>
        (0 until sideLength).forall { v =>
          area.exists { b =>
            b.x == block.x + h &&
            b.y == block.y + v
          }
        }
      }
    }
  }

  def profileArea: Double = {
    val range = (0 until maxDimension)
    range.map(countSquares).sum.toDouble / range
      .map(theoreticalMax)
      .sum
      .toDouble
  }

  private def theoreticalMax(sideLength: Int): Int = {
    val xMax = w - sideLength
    val yMax = h - sideLength
    if (xMax > 0 && yMax > 0) xMax * yMax else 0
  }

  def subSpaces(): SubSpaces = {
    @scala.annotation.tailrec
    def breakIntoComponents(
      source: AreaDefinition = this,
      components: Set[AreaDefinition] = Set.empty
    ): Set[AreaDefinition] = {
      if (source.isEmpty) {
        components
      } else {
        val thisRect = source.area
          .map(largestRectangle)
          .groupBy(_.area.size)
          .maxBy(_._1)
          ._2
          .minBy(_.leftTop)
        breakIntoComponents(source - thisRect, components + thisRect)
      }
    }
    SubSpaces(breakIntoComponents())
  }

  private def largestRectangle(block: GridBlock): AreaDefinition = {
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

  private def getRectangle(start: GridBlock, x: Int, y: Int): Option[AreaDefinition] = {
    val blocks = (0 until x).flatMap { px =>
      (0 until y).map { py =>
        start.moveX(px).moveY(py)
      }
    }.toSet
    Option.when(blocks.subsetOf(area))(AreaDefinition(blocks))
  }

  def >(that: AreaDefinition): Boolean =
    this.area.size > that.area.size

  def -(that: AreaDefinition): AreaDefinition = {
    assert(this contains that, "Can't remove what you don't have.") //@TODO remove debug code
    copy(area = area diff that.area)
  }

  def contains(that: AreaDefinition): Boolean = that.area subsetOf this.area

  def leftTop: GridBlock =
    area.groupBy(_.x).minBy(_._1)._2.minBy(_.y) //smallest x, then smallest y

  def isEmpty: Boolean = area.isEmpty

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

  case class SubSpaces(rectangles: Map[Int, AreaDefinition]) {

    def map(f: (Int, AreaDefinition) => (Int, AreaDefinition)): SubSpaces = copy(rectangles = rectangles.toSeq.map(f))

  }

  object SubSpaces {

    def apply(items: Set[AreaDefinition]): SubSpaces = new SubSpaces(items.groupBy(_.size))

    def scaleRoom(reference: SubSpaces)

  }

}

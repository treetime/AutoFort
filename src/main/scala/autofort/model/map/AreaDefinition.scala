package autofort.model.map

/*
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


  def contains(that: AreaDefinition): Boolean = that.area subsetOf this.area

  def isEmpty: Boolean = area.isEmpty

  def min(f: GridBlock => Int): Int = f(area.minBy(f))

  def max(f: GridBlock => Int): Int = f(area.maxBy(f))








  def shrinkHorizontallyTo(fraction: Double): AreaDefinition = {
    val bracket = Math.round(w * (1 - fraction) / 2).toInt
    AreaDefinition(area.filter(b => b.x >= bracket && b.x <= w - bracket))
  }



  def getSurroundingBlocks: AreaDefinition = {
    val offset = move(1, 1)
    AreaDefinition(AreaDefinition.blockField(w + 2, h + 2).filterNot(area))
  }



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
      y: Int = 0,
      currentLargest: AreaDefinition = AreaDefinition.empty
    ): AreaDefinition = {
      if (x > xMin + w) {
        findLargest(block, 1, y + 1)
      } else if (y > yMin + h) {
        currentLargest
      } else {
        getRectangle(block, x, y) match {
          case Some(thing) =>
            findLargest(
              block,
              x + 1,
              y,
              if (thing > currentLargest) thing else currentLargest
            )
          case None => findLargest(block, 1, y + 1)
        }
      }
    }
    findLargest(block)

    area.map{block =>
      findLargest(block)
    }.maxBy(_.size)

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

  def apply(area: Set[GridBlock] = Set.empty) = new AreaDefinition(area)

  /**
    * Creates a room where the shortest side of the largest rectangle in the room is "scale"
    * */
  def fromShape(shape: ShapeDefinition, scale: Int): AreaDefinition = {
    val bigScale = 10
    val reference = createAreaDefinition(shape, bigScale)
    val areaScale = scale * reference.largestRectangle.smallestDimension / bigScale.toDouble
    createAreaDefinition(shape, areaScale.toInt)
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


  def blockField(xMax: Int, yMax: Int): Set[GridBlock] = {
    (0 until xMax).flatMap { px =>
      (0 until yMax).map { py =>
        GridBlock(px, py, 0)
      }
    }.toSet
  }




}
*/